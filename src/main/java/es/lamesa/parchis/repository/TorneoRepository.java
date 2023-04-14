package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.lamesa.parchis.model.Torneo;
import es.lamesa.parchis.model.EstadoTorneo;
import java.util.List;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    
    List<Torneo> findByEstado(EstadoTorneo estado);

}
