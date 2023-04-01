package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.lamesa.parchis.model.Casilla;

public interface CasillaRepository extends JpaRepository<Casilla, Long> {
    
}
