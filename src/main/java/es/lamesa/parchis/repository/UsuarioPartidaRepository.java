package es.lamesa.parchis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.Color;

public interface UsuarioPartidaRepository extends JpaRepository<UsuarioPartida, Long> {

    @Query("SELECT up.usuario FROM UsuarioPartida up WHERE up.partida = :partida AND up.color = :color")
    Usuario obtenerUsuario(@Param("partida") Partida partida, @Param("color") Color color);

}