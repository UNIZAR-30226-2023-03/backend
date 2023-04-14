package es.lamesa.parchis.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "torneo")
public class Torneo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String nombre;

    // @Column
    // private String descripcion;
    
    @Column
    private int precioEntrada;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int numJugadores;

    @Column
    private ConfigBarreras configBarreras;

    @Column
    private ConfigFichas configFichas;
    
    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL)
    private List<Partida> partidas = new ArrayList<>(4);
    
    @OneToOne(mappedBy = "finalTorneo", cascade = CascadeType.ALL)
    private Partida partidaFinal;

    public Torneo(String nom, int precio, ConfigBarreras cb, ConfigFichas cf) {
        this.nombre = nom;
        this.precioEntrada = precio;
        this.configBarreras = cb;
        this.configFichas = cf;
    }
    
}
