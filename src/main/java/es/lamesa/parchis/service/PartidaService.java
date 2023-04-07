package es.lamesa.parchis.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.repository.TableroRepository;
import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.repository.UsuarioEstadisticasRepository;
import es.lamesa.parchis.repository.UsuarioPartidaRepository;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioEstadisticas;
import es.lamesa.parchis.model.TipoCasilla;
import es.lamesa.parchis.model.Tablero;
import es.lamesa.parchis.model.EstadoPartida;
import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.model.Color;
import es.lamesa.parchis.model.dto.RequestPartida;
import es.lamesa.parchis.model.dto.RequestPartidaPublica;
import es.lamesa.parchis.model.dto.RequestMovimiento;
import es.lamesa.parchis.model.dto.ResponsePartida;
import es.lamesa.parchis.model.dto.UsuarioColorDto;
import es.lamesa.parchis.model.dto.ResponseDado;
import es.lamesa.parchis.model.dto.ResponseMovimiento;
import es.lamesa.parchis.exception.GenericException;

@Service
public class PartidaService {
    
    @Autowired
    PartidaRepository pRepository;

    @Autowired
    TableroRepository tRepository;

    @Autowired
    UsuarioRepository uRepository;

    @Autowired
    UsuarioEstadisticasRepository ueRepository;

    @Autowired
    UsuarioPartidaRepository upRepository;
    
    @Autowired
    SimpMessagingTemplate messagingTemplate;
     
    public List<Partida> getPartidas() {
        return pRepository.findAll();
    }
    
    public ResponsePartida crearPartidaPrivada(RequestPartida request) {
        Usuario usuario = new Usuario();
        usuario.setId(request.getJugador());    
        if (!upRepository.estaJugando(usuario)) {
            if (pRepository.findByNombreAndEstado(request.getNombre()) == null) {
                Partida partida = new Partida();
                partida.setNombre(request.getNombre());
                partida.setPassword(request.getPassword());
                partida.setConfigBarreras(request.getConfiguracionB());
                partida.setConfigFichas(request.getConfiguracionF());
                partida.setEstado(EstadoPartida.ESPERANDO_JUGADORES);
                UsuarioPartida up = new UsuarioPartida();
                up.setUsuario(usuario);
                up.setPartida(partida);
                up.setColor(Color.values()[partida.getJugadores().size()]);
                partida.getJugadores().add(up);
                
                UsuarioEstadisticas ue = ueRepository.findByUsuario(usuario);
                ue.setPartidasJugadas(ue.getPartidasJugadas() + 1);
                ueRepository.save(ue);
                partida = pRepository.save(partida);
                ResponsePartida r = new ResponsePartida(partida.getId(), up.getColor());
                return r;
            }
            throw new GenericException("Nombre de sala no disponible: ya se está jugando una partida con ese nombre de sala");
        }
        throw new GenericException("Ya estás jugando una partida");
    }
    
    public ResponsePartida conectarPartidaPrivada(RequestPartida request) {
        Usuario usuario = new Usuario();
        usuario.setId(request.getJugador());
        if (!upRepository.estaJugando(usuario)) {
            Partida partida = new Partida();
            partida = pRepository.buscarPartida(request.getNombre());
            if (partida != null) {
                if (request.getPassword().contentEquals(partida.getPassword())) {
                    //UsuarioPartida:
                    UsuarioPartida up = new UsuarioPartida();
                    up.setUsuario(usuario);
                    up.setPartida(partida);
                    up.setColor(Color.values()[partida.getJugadores().size()]);
                    partida.getJugadores().add(up);
                    if (partida.getJugadores().size() == 4) {
                        partida.empezar();
                    }
                    //Estadisticas:
                    UsuarioEstadisticas ue = ueRepository.findByUsuario(usuario);
                    ue.setPartidasJugadas(ue.getPartidasJugadas() + 1);
                    ueRepository.save(ue);
                    partida = pRepository.saveAndFlush(partida);
                    //Envio de info al frontend:
                    List<UsuarioPartida> lup = upRepository.obtenerUsuarios(partida, usuario);
                    List<UsuarioColorDto> luc = new ArrayList<>();
                    for(UsuarioPartida uup: lup) {
                        UsuarioColorDto uc = new UsuarioColorDto(uup.getUsuario().getUsername(), uup.getColor());
                        luc.add(uc);
                    }
                    ResponsePartida r = new ResponsePartida(partida.getId(), up.getColor(), luc);
                    //Aviso al resto de la llegada de un nuevo jugador:
                    UsuarioColorDto uc = new UsuarioColorDto(uRepository.findById(usuario.getId()).get().getUsername(), up.getColor());
                    messagingTemplate.convertAndSend("/topic/nuevo-jugador/" + partida.getId(), uc);
                    return r;
                }
                //EXCEPCIÓN CONTRASEÑA INCORRECTA
                throw new GenericException("Contraseña incorrecta para la sala indicada");
            }
            //EXCEPCIÓN NOMBRE DE PARTIDA NO ENCONTRADO
            throw new GenericException("La partida no existe o está ya en curso");
        }
        throw new GenericException("Ya estás jugando una partida");
    }

    public Color empezarPartida(Long id) {
        Partida p = pRepository.findById(id).get();
        if (p.getJugadores().size() == 1) {
            throw new GenericException("Debe haber al menos 2 jugadores para empezar");
        }
        if (p.getEstado() == EstadoPartida.EN_PROGRESO){
            throw new GenericException("No se puede crear una partida que ya está en progreso");
        }
        p.empezar();
        pRepository.save(p);
        messagingTemplate.convertAndSend("/topic/turno/" + p.getId(), p.getTurno());
        return p.getTurno();
    }

    public ResponsePartida jugarPartidaPublica(RequestPartidaPublica p) {
        Usuario usuario = new Usuario();
        usuario.setId(p.getJugador());
        if (!upRepository.estaJugando(usuario)) {
            List<Partida> partidas = new ArrayList<>();
            Partida partida = new Partida();
            partidas = pRepository.buscarPublica();
            if (partidas.isEmpty()) {
                // Creo la partida
                partida.setConfigBarreras(p.getConfiguracionB());
                partida.setConfigFichas(p.getConfiguracionF());
                partida.setEstado(EstadoPartida.ESPERANDO_JUGADORES);
            }
            else {
                partida = partidas.get(0);
            }
            // UsuarioPartida:
            UsuarioPartida up = new UsuarioPartida();
            up.setUsuario(usuario);
            up.setPartida(partida);
            up.setColor(Color.values()[partida.getJugadores().size()]);
            partida.getJugadores().add(up);
            if (partida.getJugadores().size() == 4) {
                partida.empezar();
            }
            //Estadisticas:
            UsuarioEstadisticas ue = ueRepository.findByUsuario(usuario);
            ue.setPartidasJugadas(ue.getPartidasJugadas() + 1);
            ueRepository.save(ue);
            partida = pRepository.save(partida);
            //Envio de info al frontend:
            List<UsuarioPartida> lup = upRepository.obtenerUsuarios(partida, usuario);
            List<UsuarioColorDto> luc = new ArrayList<>();
            for(UsuarioPartida uup: lup) {
                UsuarioColorDto uc = new UsuarioColorDto(uup.getUsuario().getUsername(), uup.getColor());
                luc.add(uc);
            }
            ResponsePartida r = new ResponsePartida(partida.getId(), up.getColor(),luc);
            //Aviso al resto de la llegada de un nuevo jugador:
            UsuarioColorDto uc = new UsuarioColorDto(uRepository.findById(usuario.getId()).get().getUsername(), up.getColor());
            messagingTemplate.convertAndSend("/topic/nuevo-jugador/" + partida.getId(), uc);
            return r;
        }
        throw new GenericException("Ya estás jugando una partida");
    }

    public ResponseDado comprobarMovimientos(Long id, int dado) {
        Partida p = pRepository.findById(id).get();
        ResponseDado rd = p.comprobarMovimientos(dado);
        pRepository.save(p);
        if (rd.isSacar()) {
            messagingTemplate.convertAndSend("/topic/salida/" + p.getId(), rd);
        }
        if (rd.getComida() != null) {
            Usuario u = upRepository.obtenerUsuario(p, p.getTurno());
            UsuarioEstadisticas ue = ueRepository.findByUsuario(u);
            ue.setNumComidas(ue.getNumComidas() + 1);
            ueRepository.save(ue);
        }   
        return rd;
    }
    
    public ResponseMovimiento realizarMovimiento(RequestMovimiento request) {
        System.out.println(request.getPartida());
        Partida p = pRepository.findById(request.getPartida()).get();
        ResponseMovimiento rm = p.realizarMovimiento(request.getFicha(), request.getDado());
        Usuario u = null;
        UsuarioEstadisticas ue = null;
        if (rm.getDestino().getTipo() == TipoCasilla.META) { 
            u = upRepository.obtenerUsuario(p, p.getTurno());
            ue = ueRepository.findByUsuario(u);
            ue.setNumEnMeta(ue.getNumEnMeta() + 1);
            ueRepository.save(ue);
        }
        else if (rm.getComida() != null) {
            u = upRepository.obtenerUsuario(p, p.getTurno());
            ue = ueRepository.findByUsuario(u);
            ue.setNumComidas(ue.getNumComidas() + 1);
            ueRepository.save(ue);
        } 
        if (rm.isAcabada()) {
            u = upRepository.obtenerUsuario(p, p.getTurno());
            ue = ueRepository.findByUsuario(u);
            u.setNumMonedas(u.getNumMonedas() + 50);
            System.out.println(u.getNumMonedas());
            uRepository.save(u);
            ue.setPartidasGanadas(ue.getPartidasGanadas() + 1);
            ueRepository.save(ue);
            Tablero t = p.getTablero();
            p.setTablero(null);
            pRepository.save(p);
            tRepository.delete(t);        
        }
        else {
            pRepository.save(p);
        }
        messagingTemplate.convertAndSend("/topic/movimiento/" + p.getId(), rm);
        return rm;
    } 
}
