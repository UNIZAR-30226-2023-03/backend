package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.Amistad;

import java.util.List;

public interface AmistadRepository extends JpaRepository<Amistad, Long> {
    public List<Usuario> findByAmigoAndAceptado(Long amigo, boolean aceptado);
}
