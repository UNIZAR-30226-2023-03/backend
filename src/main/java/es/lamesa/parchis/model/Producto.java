package es.lamesa.parchis.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "producto")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int precio;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProducto tipoProducto;

    @JsonManagedReference
    @OneToMany(mappedBy = "producto", cascade = CascadeType.PERSIST)
    private List<UsuarioProducto> adquisidores = new ArrayList<>();

}
