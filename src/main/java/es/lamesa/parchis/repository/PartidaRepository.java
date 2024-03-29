package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.lamesa.parchis.model.ConfigBarreras;
import es.lamesa.parchis.model.ConfigFichas;
import es.lamesa.parchis.model.Partida;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    @Query("SELECT p FROM Partida p WHERE p.nombre = :nombre AND (p.estado = 'ESPERANDO_JUGADORES' OR p.estado = 'JUGANDO')")
    Partida findByNombreAndEstado(@Param("nombre") String nombre);

    @Query("SELECT p FROM Partida p WHERE p.nombre = :nombre AND p.estado = 'ESPERANDO_JUGADORES'")
    Partida buscarPartida(@Param("nombre") String nombre);

    @Query("SELECT p FROM Partida p WHERE p.nombre IS NULL AND p.password IS NULL AND p.estado = 'ESPERANDO_JUGADORES'")
    List<Partida> buscarPublica();

    @Query("SELECT p FROM Partida p WHERE p.nombre = :nombre AND p.estado = 'ESPERANDO_JUGADORES' AND p.configBarreras = :barreras AND p.configFichas = :fichas")
    Partida buscarPartidaPublica(@Param("nombre") String nombre, @Param("barreras") ConfigBarreras cb, @Param("fichas") ConfigFichas cf);

}
