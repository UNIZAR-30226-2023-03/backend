package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;

import es.lamesa.parchis.model.Producto;
import es.lamesa.parchis.model.dto.RequestProducto;
import es.lamesa.parchis.service.TiendaService;

@RestController
@RequestMapping("/tienda")
public class TiendaController {

    @Autowired
    TiendaService service;

    @GetMapping()
    @Operation(summary = "Obtiene todos los productos de la tienda")
    public List<Producto> getProductos() {
        return service.getProductos();
    }

    @PostMapping("/comprar")
    @Operation(summary = "Realiza la compra de un producto para un usuario concreto")
    public void realizarCompra(@RequestBody RequestProducto request) {
        service.realizarCompra(request);
    }

    @GetMapping("/tableros")
    @Operation(summary = "Obtiene todos los tableros de la tienda")
    public List<Producto> getTableros() {
        return service.getTableros();
    }

    @GetMapping("/fichas")
    @Operation(summary = "Obtiene todas las fichas de la tienda")
    public List<Producto> getFichas() {
        return service.getFichas();
    }

}
