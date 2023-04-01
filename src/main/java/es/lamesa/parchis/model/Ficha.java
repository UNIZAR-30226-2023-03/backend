package es.lamesa.parchis.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ficha")
public class Ficha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private Color color;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "casilla_id")
    private Casilla casilla;

    @Column(columnDefinition = "int default 0")
    int numPasos;
    
    public Ficha(Color color, int numero, Casilla casilla) {
        this.numero = numero;
        this.color = color;
        this.casilla = casilla;
    }
}
