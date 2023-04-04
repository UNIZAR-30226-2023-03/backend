package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.lamesa.parchis.model.Tablero;

public interface TableroRepository extends JpaRepository<Tablero, Long> {

}
