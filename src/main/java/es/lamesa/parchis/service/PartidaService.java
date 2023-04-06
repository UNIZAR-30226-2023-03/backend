package es.lamesa.parchis.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.repository.TableroRepository;
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
    UsuarioEstadisticasRepository ueRepository;

    @Autowired
    UsuarioPartidaRepository upRepository;
    
    @Autowired
    SimpMessagingTemplate messagingTemplate;
     
    public List<Partida> getPartidas() {
        return pRepository.findAll();
    }
    
    public ResponsePartida crearPartidaPrivada(RequestPartida partidaDto) {
        if (pRepository.findByNombreAndEstado(partidaDto.getNombre()) == null) {
            Partida partida = new Partida();
            partida.setNombre(partidaDto.getNombre());
            partida.setPassword(partidaDto.getPassword());
            partida.setConfigBarreras(partidaDto.getConfiguracionB());
            partida.setConfigFichas(partidaDto.getConfiguracionF());
            partida.setEstado(EstadoPartida.ESPERANDO_JUGADORES);
            Usuario usuario = new Usuario();
            usuario.setId(partidaDto.getJugador());    
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
    
    public ResponsePartida conectarPartidaPrivada(RequestPartida partidaDto) {
		Partida partida = new Partida();
		partida = pRepository.buscarPartida(partidaDto.getNombre());
        if (partida != null) {
            if (partidaDto.getPassword().contentEquals(partida.getPassword())) {
                Usuario usuario = new Usuario();
                usuario.setId(partidaDto.getJugador());
                UsuarioPartida up = new UsuarioPartida();
                up.setUsuario(usuario);
                up.setPartida(partida);
                up.setColor(Color.values()[partida.getJugadores().size()]);
                partida.getJugadores().add(up);
                if (partida.getJugadores().size() == 4) {
                    partida.empezar();
                }
                UsuarioEstadisticas ue = ueRepository.findByUsuario(usuario);
                ue.setPartidasJugadas(ue.getPartidasJugadas() + 1);
                ueRepository.save(ue);
                partida = pRepository.save(partida);
                ResponsePartida r = new ResponsePartida(partida.getId(), up.getColor());
                messagingTemplate.convertAndSend("/topic/nuevo-jugador/" + partida.getId(), up.getColor());
                return r;
            }
            //EXCEPCIÓN CONTRASEÑA INCORRECTA
            throw new GenericException("Contraseña incorrecta para la sala indicada");
        }
        //EXCEPCIÓN NOMBRE DE PARTIDA NO ENCONTRADO
        throw new GenericException("La partida no existe o está ya en curso");
    }

    public void empezarPartida(Long id) {
        Partida p = pRepository.findById(id).get();
        if (p.getJugadores().size() == 1) {
            throw new GenericException("Debe haber al menos 2 jugadores para empezar");
        }
        if (p.getEstado() == EstadoPartida.EN_PROGRESO){
            throw new GenericException("No se puede crear una partida que ya está en progreso");
        }
        p.empezar();
        pRepository.save(p);
    }

    public ResponsePartida jugarPartidaPublica(RequestPartidaPublica p) {
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
        Usuario usuario = new Usuario();
        usuario.setId(p.getJugador());
        UsuarioPartida up = new UsuarioPartida();
        up.setUsuario(usuario);
        up.setPartida(partida);
        up.setColor(Color.values()[partida.getJugadores().size()]);
        partida.getJugadores().add(up);
        if (partida.getJugadores().size() == 4) {
            partida.empezar();
        }
        UsuarioEstadisticas ue = ueRepository.findByUsuario(usuario);
        ue.setPartidasJugadas(ue.getPartidasJugadas() + 1);
        ueRepository.save(ue);
        partida = pRepository.save(partida);
        ResponsePartida r = new ResponsePartida(partida.getId(), up.getColor());
        messagingTemplate.convertAndSend("/topic/nuevo-jugador/" + partida.getId(), up.getColor());
        return r;
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
