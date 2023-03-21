package es.lamesa.parchis.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario_partida")
public class UsuarioPartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "color")
    private Color color;

    public UsuarioPartida(Usuario usuario, Partida partida, Color color) {
        this.usuario = usuario;
        this.partida = partida;
        this.color = color;
    }
}

    
