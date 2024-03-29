package es.lamesa.parchis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "usuario_partida")
public class UsuarioPartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;

    @Enumerated(EnumType.ORDINAL)
    private Color color;

    @Column(columnDefinition = "boolean default false")
    private boolean ganador;

    @Column(columnDefinition = "int default 0")
    private int numSeises;

    @Column(columnDefinition = "int default 0")
    private int numPausas;

    public UsuarioPartida(Usuario usuario, Partida partida, Color color) {
        this.usuario = usuario;
        this.partida = partida;
        this.color = color;
    }

}
