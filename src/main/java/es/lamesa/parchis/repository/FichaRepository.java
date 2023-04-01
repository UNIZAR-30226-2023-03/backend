package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.lamesa.parchis.model.Ficha;

public interface FichaRepository extends JpaRepository<Ficha, Long> {
    
}
