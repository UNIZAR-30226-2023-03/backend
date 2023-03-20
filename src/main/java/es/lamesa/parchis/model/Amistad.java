package es.lamesa.parchis.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "amistad")
public class Amistad {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "usuario1", referencedColumnName = "id")
    private Usuario usuario1;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "usuario2", referencedColumnName = "id")
    private Usuario usuario2;
    
}
