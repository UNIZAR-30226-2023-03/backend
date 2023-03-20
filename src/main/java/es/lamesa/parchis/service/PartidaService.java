package es.lamesa.parchis.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.dto.PartidaDto;
import es.lamesa.parchis.model.EstadoPartida;
import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.model.Color;

@Service
public class PartidaService {
    
    @Autowired
    PartidaRepository pRepository;

    @Autowired
    UsuarioRepository uRepository;
     
    public List<Partida> getPartidas() {
        return pRepository.findAll();
    }
    
    public Partida crearPartida(PartidaDto partidaDto) {
        if (pRepository.findByNombreAndEstado(partidaDto.getNombre()) == null) {
            Partida partida = new Partida();
            partida.setId(UUID.randomUUID().toString());
            partida.setNombre(partidaDto.getNombre());
            partida.setPassword(partidaDto.getPassword());
            partida.setConfigBarreras(partidaDto.getConfiguracion());
            partida.setEstado(EstadoPartida.EN_PROGRESO);
            UsuarioPartida up = new UsuarioPartida(uRepository.findById(partidaDto.getJugador()).get(), partida, Color.AMARILLO);
            return pRepository.save(partida);
        }
        return null;
    }

}
