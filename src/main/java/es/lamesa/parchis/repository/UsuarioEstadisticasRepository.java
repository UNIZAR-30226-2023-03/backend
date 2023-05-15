package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioEstadisticas;

public interface UsuarioEstadisticasRepository extends JpaRepository<UsuarioEstadisticas, Long> {
    
    UsuarioEstadisticas findByUsuario(Usuario usuario);

    List<UsuarioEstadisticas> findAllByOrderByPartidasJugadasDesc();

    List<UsuarioEstadisticas> findAllByOrderByPartidasGanadasDesc();

    List<UsuarioEstadisticas> findAllByOrderByNumComidasDesc();

    List<UsuarioEstadisticas> findAllByOrderByNumEnMetaDesc();

    List<UsuarioEstadisticas> findAllByOrderByTorneosJugadosDesc();

    List<UsuarioEstadisticas> findAllByOrderByTorneosGanadosDesc();

}
