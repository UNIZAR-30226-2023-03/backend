package es.lamesa.parchis.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import es.lamesa.parchis.service.UsuarioService;
import es.lamesa.parchis.model.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController{
    @Autowired
    UsuarioService service;
    
    @GetMapping()
    public ArrayList<Usuario> getUsuarios() {
        return service.getUsuarios();
    }

    @PostMapping()
    public Usuario addUsuario(@RequestBody Usuario usuario) {
        return service.addUsuario(usuario);
    }

    @PostMapping("/login")
    public boolean validarUsuario(@RequestParam("login") String login, 
                                  @RequestParam("password") String password) {
        System.out.println("Hola");
        return service.validarUsuario(login, password);
    }

    @PostMapping("/eliminar/{id}")
    public String borrarUsuario(@PathVariable("id") Long id) {
        service.borrarUsuario(id);
        return "Usuario eliminado con exito";
    }
}
