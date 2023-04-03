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
import es.lamesa.parchis.model.dto.UsuarioDto;
import es.lamesa.parchis.model.dto.RequestAmistad;
import es.lamesa.parchis.model.dto.AmigosDto;
import es.lamesa.parchis.model.dto.LoginDto;
import es.lamesa.parchis.model.dto.ResponseUsuario;
import es.lamesa.parchis.model.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;
    
    // Gestión de usuarios

    @GetMapping()
    public List<Usuario> getUsuarios() {
        return service.getUsuarios();
    }

    @PostMapping("/crear")
    public ResponseUsuario addUsuario(@RequestBody UsuarioDto usuario) {
        return service.addUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseUsuario validarUsuario(@RequestBody LoginDto request) {
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
    public List<AmigosDto> mostrarSolicitudes(@PathVariable("id") Long id) {
        return service.mostrarSolicitudes(id);
    }

    @GetMapping("/amigos/{id}")
    public List<AmigosDto> getAmigos(@PathVariable("id") Long id) {
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

    @PostMapping("/comprar/{id}")
    public void realizarCompra(@PathVariable("id") Long id, int coste) {
        service.realizarCompra(id, coste);
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

}
