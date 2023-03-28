package es.lamesa.parchis.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "amistad")
public class Amistad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "amigo_id")
    private Usuario amigo;

    @Column (nullable = false)
    private boolean aceptado;
    
}
