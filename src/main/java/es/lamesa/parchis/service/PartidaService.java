package es.lamesa.parchis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.repository.UsuarioPartidaRepository;
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

    @Autowired
    UsuarioPartidaRepository upRepository;
     
    public List<Partida> getPartidas() {
        return pRepository.findAll();
    }
    
    @Transactional
    public Partida crearPartida(PartidaDto partidaDto) {
        if (pRepository.findByNombreAndEstado(partidaDto.getNombre()) == null) {
            Partida partida = new Partida();
            partida.setNombre(partidaDto.getNombre());
            partida.setPassword(partidaDto.getPassword());
            partida.setConfigBarreras(partidaDto.getConfiguracion());
            partida.setEstado(EstadoPartida.ESPERANDO_JUGADORES);

            Usuario usuario = uRepository.findById(partidaDto.getJugador()).get();
            
            UsuarioPartida up = new UsuarioPartida();
            up.setUsuario(usuario);
            up.setPartida(partida);
            up.setColor(Color.AMARILLO);

            partida.getJugadores().add(up);
            usuario.getPartidas().add(up);
            
            partida = pRepository.save(partida);
            uRepository.save(usuario);

            return partida;
        }
        return null;
    }


}
