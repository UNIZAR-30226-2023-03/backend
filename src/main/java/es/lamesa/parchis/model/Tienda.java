package es.lamesa.parchis.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tienda")
public class Tienda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int precio;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String imagenUrl;

    // Otros atributos y métodos según tus necesidades
}
