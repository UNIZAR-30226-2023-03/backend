package es.lamesa.parchis.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.Ficha;
import es.lamesa.parchis.model.EstadoPartida;
import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.exception.GenericException;
import es.lamesa.parchis.model.Color;
import es.lamesa.parchis.model.dto.PartidaDto;
import es.lamesa.parchis.model.dto.RequestPartidaPublica;
import es.lamesa.parchis.model.dto.RequestMovimiento;
import es.lamesa.parchis.model.dto.ResponsePartida;
import es.lamesa.parchis.model.dto.ResponseDado;
import es.lamesa.parchis.model.dto.ResponseMovimiento;

@Service
public class PartidaService {
    
    @Autowired
    PartidaRepository repository;
     
    public List<Partida> getPartidas() {
        return repository.findAll();
    }
    
    public ResponsePartida crearPartidaPrivada(PartidaDto partidaDto) {
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
            up.setColor(Color.values()[partida.getJugadores().size()]);

            partida.getJugadores().add(up);
            partida = repository.save(partida);
            ResponsePartida r = new ResponsePartida(partida.getId(), up.getColor());
            return r;
        }
        //EXCEPCIÓN NOMBRE DE SALA USADO EN PARTIDA QUE SE ESTÁ JUGANDO
        throw new GenericException("Nombre de sala no disponible: ya se está jugando una partida con ese nombre de sala");
    }
    
    public ResponsePartida conectarPartidaPrivada(PartidaDto partidaDto) {
		Partida partida = new Partida();
		partida = repository.buscarPartida(partidaDto.getNombre());
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

                partida = repository.save(partida);
                ResponsePartida r = new ResponsePartida(partida.getId(), up.getColor());
                return r;
            }
            //EXCEPCIÓN CONTRASEÑA INCORRECTA
            throw new GenericException("Contraseña incorrecta para la sala indicada");
        }
        //EXCEPCIÓN NOMBRE DE PARTIDA NO ENCONTRADO
        throw new GenericException("El nombre de sala indicado no existe");
    }

    public void empezarPartida(Long id) {
        Partida p = repository.findById(id).get();
        if (p.getJugadores().size() == 1) {
            throw new GenericException("Debe haber al menos 2 jugadores para empezar");
        }
        p.empezar();
        repository.save(p);
    }

    public ResponsePartida jugarPartidaPublica(RequestPartidaPublica p) {
        List<Partida> partidas = new ArrayList<>();
        Partida partida = new Partida();

        partidas = repository.buscarPublica();
        if (partidas.isEmpty()){
            // Creo la partida
            partida.setConfigBarreras(p.getConfiguracion());
            partida.setEstado(EstadoPartida.ESPERANDO_JUGADORES);
        }
        else{
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
        
        partida = repository.save(partida);
        ResponsePartida r = new ResponsePartida(partida.getId(), up.getColor());
        return r;
    }

    public ResponseDado comprobarMovimientos(Long id, int dado) {
        Partida p = repository.findById(id).get();
        return p.comprobarMovimientos(dado);
    }
    
    public ResponseMovimiento realizarMovimiento(RequestMovimiento request) {
        Partida p = repository.findById(request.getPartida()).get();
        return p.realizarMovimiento(request.getFicha(), request.getDado());
    }
    
}
