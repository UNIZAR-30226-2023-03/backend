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
    
    @Column
    private int precioEntrada;
    
    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL)
    private List<Partida> partidas = new ArrayList<>(4);
    
    @OneToOne(mappedBy = "finalTorneo", cascade = CascadeType.ALL)
    private Partida partidaFinal;
    
}
