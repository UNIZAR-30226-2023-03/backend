package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.lamesa.parchis.model.Torneo;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    
}
