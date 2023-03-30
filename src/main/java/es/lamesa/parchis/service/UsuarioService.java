package es.lamesa.parchis.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.repository.AmistadRepository;
import es.lamesa.parchis.exception.GenericException;
import es.lamesa.parchis.model.Amistad;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.dto.AmigosDto;
import es.lamesa.parchis.model.dto.RequestAmistad;
import es.lamesa.parchis.model.dto.UsuarioDto;


@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository uRepository;
    @Autowired
    AmistadRepository aRepository;

    public List<Usuario> getUsuarios() {
        return uRepository.findAll();
    }

    public Usuario addUsuario(UsuarioDto usuario) {
        Usuario u = new Usuario();
        u.setEmail(usuario.getEmail());
        u.setUsername(usuario.getUsername());
        u.setPassword(usuario.getPassword());
        u.encriptarPassword();
        return uRepository.save(u);
    }

    public boolean validarUsuario(String login, String password) {
        Usuario usuario = uRepository.findByUsernameOrEmail(login);
        if (usuario == null) {
            throw new GenericException("El usuario no existe");
            // return false;
        }
        else {
            System.out.println(usuario.getPassword());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(password, usuario.getPassword());
        }
    }

    public void borrarUsuario(Long id) {
        uRepository.deleteById(id);
    }
    
    public boolean enviarSolicitud(RequestAmistad amistad) {
        Amistad a = new Amistad();
        Usuario usuario = new Usuario();
        Usuario amigo = new Usuario();
        amigo.setId(amistad.getAmigo());
        a.setAmigo(amigo);
        usuario.setId(amistad.getUsuario());
        a.setUsuario(usuario);                                   
        a.setAceptado(false);
        aRepository.save(a);            
        return true;
    }

    public List<AmigosDto> mostrarSolicitudes(Long id) {
        Usuario u = new Usuario();
        u.setId(id);
        List<Usuario> us = aRepository.findSolicitudes(u);
        List<AmigosDto> am = new ArrayList<AmigosDto>();
        for (Usuario uu : us){
            AmigosDto a = new AmigosDto(uu.getId(), uu.getUsername());
            am.add(a);
        }
        return am;
    }

    public List<AmigosDto> getAmigos(Long id) {
        Usuario u = new Usuario();
        u.setId(id);
        List<Amistad> la = aRepository.findAmigos(u);
        List<AmigosDto> amigos = new ArrayList<>();
        for (Amistad a : la) {
            AmigosDto amigo = new AmigosDto(a.getIdAmigo(id), a.getUsernameAmigo(id));
            amigos.add(amigo);
        }
        return amigos;
    }

    public void aceptarSolicitud(RequestAmistad amistad){
        // aceptar solicitudes -> pasar en bd de la lista de solicitudes a la de amigos
        Usuario usuario = new Usuario();
        Usuario amigo = new Usuario();
        usuario.setId(amistad.getAmigo());
        amigo.setId(amistad.getUsuario());
        Amistad a = aRepository.findByUsuarioAndAmigo(usuario, amigo);
        a.setAceptado(true);
        aRepository.save(a);
    }

    public void denegarSolicitud(RequestAmistad amistad) {
        // denegar solicitud -> eliminar en bd de la lista de solicitudes
        Usuario usuario = new Usuario();
        Usuario amigo = new Usuario();
        usuario.setId(amistad.getAmigo());
        amigo.setId(amistad.getUsuario());
        Amistad a = aRepository.findByUsuarioAndAmigo(usuario, amigo);
        aRepository.delete(a);
    }

    public Long obtenerId(String name) {
        Usuario u = uRepository.findByUsername(name);
        return u.getId();
    }

    public void eliminarAmigo(RequestAmistad request){
        Usuario usuario = new Usuario();
        Usuario amigo = new Usuario();
        amigo.setId(request.getAmigo());
        usuario.setId(request.getUsuario());
        Amistad a = aRepository.findAmistad(usuario, amigo);
        aRepository.delete(a);
    }
}
