package es.lamesa.parchis.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.dto.PartidaDto;
import es.lamesa.parchis.model.EstadoPartida;

@Service
public class PartidaService {
    @Autowired
    PartidaRepository repository;
    
    public Partida crearPartida(PartidaDto partidaDto) {
        Partida partida = new Partida(partidaDto.getNombre(), 
                          partidaDto.getJugador(), partidaDto.getConfiguracion(), EstadoPartida.ESPERANDO_JUGADORES);
        return repository.save(partida);
    }

}
