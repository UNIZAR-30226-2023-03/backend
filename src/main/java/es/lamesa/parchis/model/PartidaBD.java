package es.lamesa.parchis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "partida")
public class PartidaBD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String ganador;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGanador() {
        return this.ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

}