package es.lamesa.parchis.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "amistad")
public class Amistad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "amigo_id")
    private Usuario amigo;

    @Column(nullable = false)
    private boolean aceptado;
    
}
