package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.lamesa.parchis.model.UsuarioPartida;

public interface UsuarioPartidaRepository extends JpaRepository<UsuarioPartida, Long> {
    
}
