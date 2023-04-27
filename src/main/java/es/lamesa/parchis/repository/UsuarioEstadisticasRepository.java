package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioEstadisticas;

public interface UsuarioEstadisticasRepository extends JpaRepository<UsuarioEstadisticas, Long> {
    
    UsuarioEstadisticas findByUsuario(Usuario usuario);

    List<UsuarioEstadisticas> findAllByOrderByPartidasJugadas();

    List<UsuarioEstadisticas> findAllByOrderByPartidasGanadas();

    List<UsuarioEstadisticas> findAllByOrderByNumComidas();

    List<UsuarioEstadisticas> findAllByOrderByNumEnMeta();

    List<UsuarioEstadisticas> findAllByOrderByTorneosJugados();

    List<UsuarioEstadisticas> findAllByOrderByTorneosGanados();

}
