package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.lamesa.parchis.service.UsuarioService;
import es.lamesa.parchis.model.dto.RequestUsuario;
import es.lamesa.parchis.model.dto.RequestAmistad;
import es.lamesa.parchis.model.dto.ResponseAmistad;
import es.lamesa.parchis.model.dto.RequestLogin;
import es.lamesa.parchis.model.dto.ResponseUsuario;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioProducto;
import es.lamesa.parchis.model.dto.RequestProducto;
import es.lamesa.parchis.model.dto.RequestCambio;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;
    
    // Gestión de usuarios

    @GetMapping()
    public List<Usuario> getUsuarios() {
        return service.getUsuarios();
    }

    @PostMapping("/crear")
    public ResponseUsuario addUsuario(@RequestBody RequestUsuario usuario) {
        return service.addUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseUsuario validarUsuario(@RequestBody RequestLogin request) {
        return service.validarUsuario(request.getLogin(), request.getPassword());
    }

    @PostMapping("/eliminar/{id}")
    public String borrarUsuario(@PathVariable("id") Long id) {
        service.borrarUsuario(id);
        return "Usuario eliminado con exito";
    }

    // Gestión de amigos

    @PostMapping("/enviar-solicitud")
    public boolean enviarSolicitud(@RequestBody RequestAmistad request) {
        return service.enviarSolicitud(request);
    }

    @GetMapping("/solicitudes/{id}")
    public List<ResponseAmistad> mostrarSolicitudes(@PathVariable("id") Long id) {
        return service.mostrarSolicitudes(id);
    }

    @GetMapping("/amigos/{id}")
    public List<ResponseAmistad> getAmigos(@PathVariable("id") Long id) {
        return service.getAmigos(id);
    }

    @PostMapping("/aceptar-solicitud")
    public void aceptarSolicitud(@RequestBody RequestAmistad request) {
        service.aceptarSolicitud(request);
    }

    @PostMapping("/denegar-solicitud")
    public void denegarSolicitud(@RequestBody RequestAmistad request) {
        service.denegarSolicitud(request);
    }

    @PostMapping("/eliminar-amigo")
    public void eliminarAmigo(@RequestBody RequestAmistad request) {
        service.eliminarAmigo(request);
    }

    @PostMapping("/premio/{id}")
	public void actualizarMonedas(@PathVariable("id") Long id, int premio) {
		service.actualizarMonedas(id, premio);
	}

    @GetMapping("/monedas/{id}")
    public int obtenerNumMonedas(@PathVariable("id") Long id) {
        return service.obtenerNumMonedas(id);
    }

    @GetMapping("/username/{id}")
    public String obtenerUsername(@PathVariable("id") Long id) {
        return service.obtenerUsername(id);
    }

    @GetMapping("/obtener-id")
    public Long obtenerId(String name) {
        return service.obtenerId(name);
    }

    @PostMapping("/activar")
    public void activarProducto(@RequestBody RequestProducto request) {
        service.activarProducto(request);
    }

    @GetMapping("/productos/{id}")
    public List<UsuarioProducto> getProductos(@PathVariable("id") Long id) {
        return service.getProductos(id);
    }

    @PostMapping("/actualizar/username")
    public void actualizarUsername(RequestCambio request) {
        service.actualizarUsername(request);
    }
    
    @PostMapping("/actualizar/email")
    public void actualizarEmail(RequestCambio request) {
        service.actualizarEmail(request);
    }

    @PostMapping("/actualizar/password")
    public void actualizarPassword(RequestCambio request) {
        service.actualizarPassword(request);
    }
    
}
