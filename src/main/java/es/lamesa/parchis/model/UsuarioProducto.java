package es.lamesa.parchis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "usuario_producto")
public class UsuarioProducto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean activo;

    public UsuarioProducto(Usuario usuario, Producto producto, boolean activo) {
        this.usuario = usuario;
        this.producto = producto;
        this.activo = activo;
    }

}
