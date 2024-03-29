package es.lamesa.parchis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.Color;

public interface UsuarioPartidaRepository extends JpaRepository<UsuarioPartida, Long> {

    UsuarioPartida findByUsuarioAndPartida(Usuario usuario, Partida partida);
    
    @Query("SELECT up.usuario FROM UsuarioPartida up WHERE up.partida = :partida AND up.color = :color")
    Usuario obtenerUsuario(@Param("partida") Partida partida, @Param("color") Color color);

    @Query("SELECT DISTINCT up FROM UsuarioPartida up WHERE up.partida = :partida AND up.usuario <> :usuario")
    List<UsuarioPartida> obtenerUsuarios(@Param("partida") Partida partida, @Param("usuario") Usuario usuario);

    @Query("SELECT COUNT(up) > 0 FROM UsuarioPartida up WHERE up.usuario = :usuario AND up.partida.estado IN ('EN_PROGRESO', 'ESPERANDO_JUGADORES')")
    boolean estaJugando(@Param("usuario") Usuario usuario);

    @Query("SELECT up.partida.id FROM UsuarioPartida up WHERE up.usuario = :usuario AND up.partida.estado = 'ESPERANDO_JUGADORES'")
    Long getPartida(@Param("usuario") Usuario usuario);

    @Query("SELECT up.partida.id FROM UsuarioPartida up WHERE up.usuario = :usuario AND up.partida.estado = 'EN_PROGRESO' AND up.partida.enPausa = true")
    Long getPartidaReconectar(@Param("usuario") Usuario usuario);

}
