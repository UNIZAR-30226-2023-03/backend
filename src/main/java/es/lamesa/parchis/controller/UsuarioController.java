package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;

import es.lamesa.parchis.service.UsuarioService;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioProducto;
import es.lamesa.parchis.model.dto.RequestUsuario;
import es.lamesa.parchis.model.dto.RequestAmistad;
import es.lamesa.parchis.model.dto.ResponseAmistad;
import es.lamesa.parchis.model.dto.RequestLogin;
import es.lamesa.parchis.model.dto.ResponseUsuario;
import es.lamesa.parchis.model.dto.RequestProducto;
import es.lamesa.parchis.model.dto.RequestCambio;
import es.lamesa.parchis.model.dto.ResponseEstadisticas;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;
    
    // Gestión de usuarios

    @GetMapping()
    @Operation(summary = "Obtiene todos los usuarios registrados")
    public List<Usuario> getUsuarios() {
        return service.getUsuarios();
    }

    @PostMapping("/crear")
    @Operation(summary = "Registra un nuevo usuario")
    public ResponseUsuario addUsuario(@RequestBody RequestUsuario usuario) {
        return service.addUsuario(usuario);
    }

    @PostMapping("/login")
    @Operation(summary = "Inicio de sesión para un usuario ya creado")
    public ResponseUsuario validarUsuario(@RequestBody RequestLogin request) {
        return service.validarUsuario(request.getLogin(), request.getPassword());
    }

    @PostMapping("/eliminar/{id}")
    @Operation(summary = "Elimina el usuario con el id dado")
    public void borrarUsuario(@PathVariable("id") Long id) {
        service.borrarUsuario(id);
    }

    // Gestión de amigos

    @PostMapping("/enviar-solicitud")
    @Operation(summary = "Envía una solicitud de amistad")
    public boolean enviarSolicitud(@RequestBody RequestAmistad request) {
        return service.enviarSolicitud(request);
    }

    @GetMapping("/solicitudes/{id}")
    @Operation(summary = "Muestra todas las solicitudes de amistad pendientes para un usuario dado")
    public List<ResponseAmistad> mostrarSolicitudes(@PathVariable("id") Long id) {
        return service.mostrarSolicitudes(id);
    }

    @GetMapping("/amigos/{id}")
    @Operation(summary = "Obtiene todos los amigos de un usuario dado")
    public List<ResponseAmistad> getAmigos(@PathVariable("id") Long id) {
        return service.getAmigos(id);
    }

    @PostMapping("/aceptar-solicitud")
    @Operation(summary = "Acepta una solicitud de amistad recibida")
    public void aceptarSolicitud(@RequestBody RequestAmistad request) {
        service.aceptarSolicitud(request);
    }

    @PostMapping("/denegar-solicitud")
    @Operation(summary = "Deniega una solicitud de amistad recibida")
    public void denegarSolicitud(@RequestBody RequestAmistad request) {
        service.denegarSolicitud(request);
    }

    @PostMapping("/eliminar-amigo")
    @Operation(summary = "Elimina un amigo de la lista")
    public void eliminarAmigo(@RequestBody RequestAmistad request) {
        service.eliminarAmigo(request);
    }

    @PostMapping("/premio/{id}")
    @Operation(summary = "Actualiza el número de monedas de un usuario dado")
	public void actualizarMonedas(@PathVariable("id") Long id, int premio) {
		service.actualizarMonedas(id, premio);
	}

    @GetMapping("/monedas/{id}")
    @Operation(summary = "Obtiene el número de monedas de un usuario dado")
    public int obtenerNumMonedas(@PathVariable("id") Long id) {
        return service.obtenerNumMonedas(id);
    }

    @GetMapping("/username/{id}")
    @Operation(summary = "Obtiene el username de un usuario dado")
    public String obtenerUsername(@PathVariable("id") Long id) {
        return service.obtenerUsername(id);
    }

    @GetMapping("/obtener-id")
    @Operation(summary = "Obtiene el id de un usuario dado su username")
    public Long obtenerId(String name) {
        return service.obtenerId(name);
    }

    @PostMapping("/activar")
    @Operation(summary = "Activa un producto comprado")
    public void activarProducto(@RequestBody RequestProducto request) {
        service.activarProducto(request);
    }

    @GetMapping("/productos/{id}")
    @Operation(summary = "Obtiene todos los productos comprados por un usuario dado")
    public List<UsuarioProducto> getProductos(@PathVariable("id") Long id) {
        return service.getProductos(id);
    }

    @PostMapping("/actualizar/username")
    @Operation(summary = "Actualiza el username de un usuario")
    public void actualizarUsername(@RequestBody RequestCambio request) {
        service.actualizarUsername(request);
    }
    
    @PostMapping("/actualizar/email")
    @Operation(summary = "Actualiza el email de un usuario")
    public void actualizarEmail(@RequestBody RequestCambio request) {
        service.actualizarEmail(request);
    }

    @PostMapping("/actualizar/password")
    @Operation(summary = "Actualiza la contraseña de un usuario")
    public void actualizarPassword(@RequestBody RequestCambio request) {
        service.actualizarPassword(request);
    }

    @PostMapping("/ranking")
    @Operation(summary = "Obtiene el ranking con las estadísticas de todos los usuarios")
    public List<ResponseEstadisticas> getRanking() {
        return service.getRanking();
    }

    @PostMapping("/estadisticas/{id}")
    @Operation(summary = "Obtiene las estadísticas personales del usuarios id")
    public ResponseEstadisticas getEstadisticas(@PathVariable("id") Long id) {
        return service.getEstadisticas(id);
    }
    
}
