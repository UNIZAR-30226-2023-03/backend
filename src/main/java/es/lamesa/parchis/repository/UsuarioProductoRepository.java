package es.lamesa.parchis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.lamesa.parchis.model.UsuarioProducto;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.Producto;


public interface UsuarioProductoRepository extends JpaRepository<UsuarioProducto, Long> {

    UsuarioProducto findByUsuarioAndProducto(Usuario usuario, Producto producto);

    @Query("SELECT up FROM UsuarioProducto up WHERE up.usuario = :usuario")
    List<UsuarioProducto> findByUsuario(Usuario usuario);

    @Query("SELECT COUNT(up) > 0 FROM UsuarioProducto up WHERE up.usuario = :usuario AND up.producto = :producto")
    boolean productoComprado(@Param("usuario") Usuario usuario, @Param("producto") Producto producto);

    @Query("SELECT up.producto FROM UsuarioProducto up WHERE up.usuario = :usuario AND up.activo = true")
    Producto getProductoActivado(@Param("usuario") Usuario usuario);

}
