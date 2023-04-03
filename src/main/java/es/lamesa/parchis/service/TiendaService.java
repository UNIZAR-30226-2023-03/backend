package es.lamesa.parchis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import es.lamesa.parchis.model.TipoProducto;

import es.lamesa.parchis.model.UsuarioProducto;
import es.lamesa.parchis.model.Producto;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.dto.RequestProducto;
import es.lamesa.parchis.repository.ProductoRepository;
import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.repository.UsuarioProductoRepository;
import es.lamesa.parchis.exception.GenericException;

@Service
public class TiendaService {

    @Autowired
    ProductoRepository pRepository;

    @Autowired
    UsuarioRepository uRepository;

    @Autowired
    UsuarioProductoRepository upRepository;

    public List<Producto> getProductos() {
        return pRepository.findAll();
    }

    public void realizarCompra(RequestProducto request) {
        Usuario u = uRepository.findById(request.getUsuario()).get();
        Producto p = pRepository.findById(request.getProducto()).get();
        if (u.getNumMonedas() < p.getPrecio()) {
            throw new GenericException("No tienes monedas suficientes");
        }
        if (upRepository.productoComprado(u, p)) {
            throw new GenericException("El producto ya se ha adquirido"); 
        }
        u.setNumMonedas(u.getNumMonedas() - p.getPrecio());
        UsuarioProducto up = new UsuarioProducto();
        up.setUsuario(u);
        up.setProducto(p);
        u.getProductos().add(up);
        upRepository.save(up);
    }

    public List<Producto> getTableros() {
        return pRepository.findByTipoProducto(TipoProducto.TABLERO);
    }

    public List<Producto> getFichas() {
        return pRepository.findByTipoProducto(TipoProducto.FICHA);
    }

    

}
