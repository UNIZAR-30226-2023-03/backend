package es.lamesa.parchis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario_estadisticas")
public class UsuarioEstadisticas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int partidasJugadas;
    
    @Column(nullable = false, columnDefinition = "int default 0")
    private int partidasGanadas;
    
    @Column(nullable = false, columnDefinition = "int default 0")
    private int numComidas;
    
    @Column(nullable = false, columnDefinition = "int default 0")
    private int numEnMeta;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int torneosJugados;
    
    @Column(nullable = false, columnDefinition = "int default 0")
    private int torneosGanados;

}
