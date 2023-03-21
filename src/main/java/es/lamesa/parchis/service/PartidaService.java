package es.lamesa.parchis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.EstadoPartida;
import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.model.Color;
import es.lamesa.parchis.model.dto.PartidaDto;

@Service
public class PartidaService {
    
    @Autowired
    PartidaRepository repository;
     
    public List<Partida> getPartidas() {
        return repository.findAll();
    }
    
    public Partida crearPartidaPrivada(PartidaDto partidaDto) {
        if (repository.findByNombreAndEstado(partidaDto.getNombre()) == null) {
            Partida partida = new Partida();
            partida.setNombre(partidaDto.getNombre());
            partida.setPassword(partidaDto.getPassword());
            partida.setConfigBarreras(partidaDto.getConfiguracion());
            partida.setEstado(EstadoPartida.ESPERANDO_JUGADORES);

            Usuario usuario = new Usuario();
            usuario.setId(partidaDto.getJugador());
            
            UsuarioPartida up = new UsuarioPartida();
            up.setUsuario(usuario);
            up.setPartida(partida);
            up.setColor(Color.AMARILLO);

            partida.getJugadores().add(up);
            
            return repository.save(partida);
        }
        return null;
    }
    
    public Partida conectarPartidaPrivada(PartidaDto partidaDto) {
		Partida partida = new Partida();
		partida = repository.buscarPartida(partidaDto.getNombre(), partidaDto.getConfiguracion());
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
                    partida.setEstado(EstadoPartida.EN_PROGRESO);
                }

                partida = repository.save(partida);
                return partida;
            }
        }
        return null;
    }
}
