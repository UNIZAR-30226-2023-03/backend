package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioEstadisticas;

public interface UsuarioEstadisticasRepository extends JpaRepository<UsuarioEstadisticas, Long> {
    
    UsuarioEstadisticas findByUsuario(Usuario usuario);

}
