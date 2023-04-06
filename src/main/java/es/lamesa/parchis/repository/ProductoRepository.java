package es.lamesa.parchis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.lamesa.parchis.model.Producto;
import es.lamesa.parchis.model.TipoProducto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByTipoProducto(TipoProducto tipoProducto);

    Producto findByNombre(String nombre);
}
