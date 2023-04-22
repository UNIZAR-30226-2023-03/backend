package es.lamesa.parchis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;

import es.lamesa.parchis.repository.TorneoRepository;
import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.repository.UsuarioPartidaRepository;
import es.lamesa.parchis.repository.UsuarioEstadisticasRepository;
import es.lamesa.parchis.model.Color;
import es.lamesa.parchis.model.ConfigBarreras;
import es.lamesa.parchis.model.ConfigFichas;
import es.lamesa.parchis.model.EstadoPartida;
import es.lamesa.parchis.model.Torneo;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioEstadisticas;
import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.EstadoTorneo;
import es.lamesa.parchis.model.dto.RequestTorneo;
import es.lamesa.parchis.model.dto.ResponsePartida;
import es.lamesa.parchis.model.dto.ResponseTorneo;
import es.lamesa.parchis.model.dto.UsuarioColorDto;
import es.lamesa.parchis.util.RandomGenerator;
import es.lamesa.parchis.exception.GenericException;

@Service
public class TorneoService {
    
    @Autowired
    TorneoRepository tRepository;

    @Autowired
    PartidaRepository pRepository;

    @Autowired
    UsuarioRepository uRepository;

    @Autowired
    UsuarioPartidaRepository upRepository;
    
    @Autowired
    UsuarioEstadisticasRepository ueRepository;
    
    @Autowired
    SimpMessagingTemplate messagingTemplate;
     
    public List<Torneo> getTorneos() {
        return tRepository.findByEstado(EstadoTorneo.ESPERANDO_JUGADORES);
    }
    
    @Async
    @Scheduled(cron = "0 0 13 * * ?", zone = "Europe/Madrid")
    public void crearTorneoSeguroRapido() {
        RandomGenerator random = new RandomGenerator();
        Torneo t = new Torneo("TORNEO RÁPIDO", random.generarEntradaTorneoRapido(), ConfigBarreras.SOLO_SEGUROS, ConfigFichas.RAPIDO, EstadoTorneo.ESPERANDO_JUGADORES);
        tRepository.save(t);
    }

    @Async
    @Scheduled(cron = "0 0 15 * * ?", zone = "Europe/Madrid")
    public void crearTorneoTodoRapido() {
        RandomGenerator random = new RandomGenerator();
        Torneo t = new Torneo("TORNEO RÁPIDO", random.generarEntradaTorneoRapido(), ConfigBarreras.TODAS_CASILLAS, ConfigFichas.RAPIDO, EstadoTorneo.ESPERANDO_JUGADORES);
        tRepository.save(t);
    }

    @Async
    @Scheduled(cron = "0 0 18 * * ?", zone = "Europe/Madrid")
    public void crearTorneoSeguroNormal() {
        RandomGenerator random = new RandomGenerator();
        Torneo t = null;
        int entrada = random.generarEntradaTorneoNormal();
        String nombre = "";
        if (entrada == 300) {
            nombre = "SUPER TORNEO";
        }
        else {
            nombre = "TORNEO NORMAL";
        }
        t = new Torneo(nombre, entrada, ConfigBarreras.SOLO_SEGUROS, ConfigFichas.NORMAL, EstadoTorneo.ESPERANDO_JUGADORES);
        tRepository.save(t);
    }
    
    @Async
    @Scheduled(cron = "0 0 21 * * ?", zone = "Europe/Madrid")
    public void crearTorneoTodoNormal() {
        RandomGenerator random = new RandomGenerator();
        Torneo t = null;
        int entrada = random.generarEntradaTorneoNormal();
        String nombre = "";
        if (entrada == 300) {
            nombre = "SUPER TORNEO";
        }
        else {
            nombre = "TORNEO NORMAL";
        }
        t = new Torneo(nombre, entrada, ConfigBarreras.TODAS_CASILLAS, ConfigFichas.NORMAL, EstadoTorneo.ESPERANDO_JUGADORES);
        tRepository.save(t);
    }

    public ResponseTorneo apuntarATorneo(RequestTorneo rt) {
        ResponseTorneo rtt = null;
        Usuario u = uRepository.findById(rt.getUsuario()).get();
        Torneo t = tRepository.findById(rt.getTorneo()).get();
        if (t.getNumJugadores() == 16) {
            throw new GenericException("El torneo está completo");
        }
        if (t.getPrecioEntrada() > u.getNumMonedas()) {
            throw new GenericException("No tienes monedas suficientes eres pobre");
        }
        t.setNumJugadores(1 + t.getNumJugadores());
        int num = t.getNumJugadores();
        if (num == 8) { //en teoría es 16
            messagingTemplate.convertAndSend("/topic/torneo/" + t.getId(), "Torneo abierto");
            for (int i = 0; i < 4; ++i) {
                Partida p = new Partida();     
                p.setConfigBarreras(t.getConfigBarreras());                
                p.setConfigFichas(t.getConfigFichas());
                p.setEstado(EstadoPartida.ESPERANDO_JUGADORES);
                p.setTorneo(t);
                t.getPartidas().add(p);
            }
            Partida p = new Partida();
            p.setConfigBarreras(t.getConfigBarreras());
            p.setConfigFichas(t.getConfigFichas());
            p.setEstado(EstadoPartida.ESPERANDO_JUGADORES);
            p.setFinalTorneo(t);
            t.setPartidaFinal(p);
            rtt = new ResponseTorneo(false, true);
        }
        else {
            rtt = new ResponseTorneo(true, false);
        }
        tRepository.save(t);
        return rtt;
    }

    public ResponsePartida jugarTorneo(RequestTorneo rt) {
        Usuario u = new Usuario();
        u.setId(rt.getUsuario());
        if (upRepository.estaJugando(u)) {
            throw new GenericException("Ya estás jugando una partida");
        }
        Torneo t = tRepository.findById(rt.getTorneo()).get();
        Partida p = null;
        int i = 0;
        for (Partida partida : t.getPartidas()) {
            if (partida.getJugadores().size() < 4) {
                p = partida;
                break;
            }
            i++;
        }
        // TODO: RESTAR MONEDAS -> PRECIO ENTRADA
        UsuarioPartida up = new UsuarioPartida();
        up.setUsuario(u);
        up.setPartida(p);
        up.setColor(Color.values()[p.getJugadores().size()]);
        p.getJugadores().add(up);
        if (p.getJugadores().size() == 4) {
            if (i == 3) {
                t.setEstado(EstadoTorneo.EN_PROGRESO); // si no lo cambia es que falta el save
            }
            p.empezar();
            messagingTemplate.convertAndSend("/topic/turno/" + p.getId(), p.getTurno());
        }
        // Estadisticas:
        UsuarioEstadisticas ue = ueRepository.findByUsuario(u);
        ue.setPartidasJugadas(ue.getPartidasJugadas() + 1);
        ue.setTorneosJugados(1 + ue.getTorneosJugados());
        ueRepository.save(ue);
        p = pRepository.save(p);
        // Envio de info al frontend:
        List<UsuarioPartida> lup = upRepository.obtenerUsuarios(p, u);
        List<UsuarioColorDto> luc = new ArrayList<>();
        for (UsuarioPartida uup : lup) {
            UsuarioColorDto uc = new UsuarioColorDto(uup.getUsuario().getUsername(), uup.getColor());
            luc.add(uc);
        }
        ResponsePartida r = new ResponsePartida(p.getId(), up.getColor(), luc);
        // Aviso al resto de la llegada de un nuevo jugador:
        UsuarioColorDto uc = new UsuarioColorDto(uRepository.findById(u.getId()).get().getUsername(), up.getColor());
        messagingTemplate.convertAndSend("/topic/nuevo-jugador/" + p.getId(), uc);
        return r;
    }

    public ResponsePartida jugarFinal(RequestTorneo rt) {
        Usuario u = new Usuario();
        u.setId(rt.getUsuario());
        Torneo t = tRepository.findById(rt.getTorneo()).get();
        Partida p = t.getPartidaFinal();
        UsuarioPartida up = new UsuarioPartida();
        up.setUsuario(u);
        up.setPartida(p);
        up.setColor(Color.values()[p.getJugadores().size()]);
        p.getJugadores().add(up);
        if (p.getJugadores().size() == 4) {
            p.empezar();
            messagingTemplate.convertAndSend("/topic/turno/" + p.getId(), p.getTurno());
        }
        // Estadisticas:
        UsuarioEstadisticas ue = ueRepository.findByUsuario(u);
        ue.setPartidasJugadas(ue.getPartidasJugadas() + 1);
        ueRepository.save(ue);
        p = pRepository.save(p);
        // Envio de info al frontend:
        List<UsuarioPartida> lup = upRepository.obtenerUsuarios(p, u);
        List<UsuarioColorDto> luc = new ArrayList<>();
        for (UsuarioPartida uup : lup) {
            UsuarioColorDto uc = new UsuarioColorDto(uup.getUsuario().getUsername(), uup.getColor());
            luc.add(uc);
        }
        ResponsePartida r = new ResponsePartida(p.getId(), up.getColor(), luc);
        // Aviso al resto de la llegada de un nuevo jugador:
        UsuarioColorDto uc = new UsuarioColorDto(uRepository.findById(u.getId()).get().getUsername(), up.getColor());
        messagingTemplate.convertAndSend("/topic/nuevo-jugador/" + p.getId(), uc);
        return r;
    }

    public void desapuntarDeTorneo(RequestTorneo rt) {
        Torneo t = tRepository.findById(rt.getTorneo()).get();
        if (t.getNumJugadores() == 16) {
            throw new GenericException("El torneo ha comenzado");
        }
        t.setNumJugadores(t.getNumJugadores() - 1);
        tRepository.save(t);
    }

}
