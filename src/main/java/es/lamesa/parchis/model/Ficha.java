package es.lamesa.parchis.model;

import lombok.Data;
import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "casilla_id")
    private Casilla casilla;
    
}
