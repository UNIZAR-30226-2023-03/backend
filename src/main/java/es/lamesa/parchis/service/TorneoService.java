package es.lamesa.parchis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;

import es.lamesa.parchis.repository.TorneoRepository;
import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.model.ConfigBarreras;
import es.lamesa.parchis.model.ConfigFichas;
import es.lamesa.parchis.model.Torneo;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.dto.RequestTorneo;
import es.lamesa.parchis.model.dto.ResponseTorneo;
import es.lamesa.parchis.util.RandomGenerator;
import es.lamesa.parchis.exception.GenericException;

@Service
public class TorneoService {
    
    @Autowired
    TorneoRepository tRepository;

    @Autowired
    UsuarioRepository uRepository;
    
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    RandomGenerator random;
     
    public List<Torneo> getTorneos() {
        return tRepository.findAll();
    }
    
    @Async
    @Scheduled(cron = "0 0 12 30 * ?", zone = "Europe/Madrid")
    public void crearTorneoSeguroRapido() {
        Torneo t = new Torneo("TORNEO RÁPIDO", random.generarEntradaTorneoRapido(), ConfigBarreras.SOLO_SEGUROS, ConfigFichas.RAPIDO);
        tRepository.save(t);
    }

    @Async
    @Scheduled(cron = "0 0 15 * * ?", zone = "Europe/Madrid")
    public void crearTorneoTodoRapido() {
        Torneo t = new Torneo("TORNEO RÁPIDO", random.generarEntradaTorneoRapido(), ConfigBarreras.TODAS_CASILLAS, ConfigFichas.RAPIDO);
        tRepository.save(t);
    }

    @Async
    @Scheduled(cron = "0 0 18 * * ?", zone = "Europe/Madrid")
    public void crearTorneoSeguroNormal() {
        Torneo t = null;
        int entrada = random.generarEntradaTorneoNormal();
        String nombre = "";
        if (entrada == 300) {
            nombre = "SUPER TORNEO";
        }
        else {
            nombre = "TORNEO NORMAL";
        }
        t = new Torneo(nombre, entrada, ConfigBarreras.SOLO_SEGUROS, ConfigFichas.NORMAL);
        tRepository.save(t);
    }
    
    @Async
    @Scheduled(cron = "0 0 21 * * ?", zone = "Europe/Madrid")
    public void crearTorneoTodoNormal() {
        Torneo t = null;
        int entrada = random.generarEntradaTorneoNormal();
        String nombre = "";
        if (entrada == 300) {
            nombre = "SUPER TORNEO";
        }
        else {
            nombre = "TORNEO NORMAL";
        }
        t = new Torneo(nombre, entrada, ConfigBarreras.TODAS_CASILLAS, ConfigFichas.NORMAL);
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
        if (num == 16) {
            messagingTemplate.convertAndSend("/topic/torneo/" + t.getId(), "Torneo abierto");
            for (int i = 0; i < 4; ++i) {
                Partida p = new Partida(false, t, t.getConfigBarreras(), t.getConfigFichas());
                t.getPartidas().add(p);
            }
            Partida p = new Partida(true, t, t.getConfigBarreras(), t.getConfigFichas());
            t.setPartidaFinal(p);
            tRepository.save(t);
            rtt = new ResponseTorneo(false, true);
        }
        else {
            rtt = new ResponseTorneo(true, false);
        }
        return rtt;
    }

}
