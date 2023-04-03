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
import es.lamesa.parchis.model.dto.ResponseUsuario;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository uRepository;
    @Autowired
    AmistadRepository aRepository;

    public List<Usuario> getUsuarios() {
        return uRepository.findAll();
    }

    public ResponseUsuario addUsuario(UsuarioDto usuario) {
        if (uRepository.findByEmail(usuario.getEmail()) != null) {
            throw new GenericException("Ya existe un usuario con ese email");
        }
        if (uRepository.findByUsername(usuario.getUsername()) != null) {
            throw new GenericException("Ya existe un usuario con ese username");
        }

        Usuario u = new Usuario();
        u.setEmail(usuario.getEmail());
        u.setUsername(usuario.getUsername());
        u.setPassword(usuario.getPassword());
        u.encriptarPassword();
        u = uRepository.save(u);
        ResponseUsuario ru = new ResponseUsuario(u.getId(), u.getEmail(), u.getUsername(), u.getNumMonedas());
        return ru;
    }

    public ResponseUsuario validarUsuario(String login, String password) {
        Usuario u = uRepository.findByUsernameOrEmail(login);
        if (u == null) {
            throw new GenericException("El usuario no existe");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(password, u.getPassword())) {
            ResponseUsuario ru = new ResponseUsuario(u.getId(), u.getEmail(), u.getUsername(), u.getNumMonedas());
            return ru;
        }
        else {
            throw new GenericException("La contraseña no es correcta");
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

    public void eliminarAmigo(RequestAmistad request){
        Usuario usuario = new Usuario();
        Usuario amigo = new Usuario();
        amigo.setId(request.getAmigo());
        usuario.setId(request.getUsuario());
        Amistad a = aRepository.findAmistad(usuario, amigo);
        aRepository.delete(a);
    }

    public void actualizarMonedas(Long id, int premio){
        Usuario u = uRepository.findById(id).get();
        u.setNumMonedas(premio);
        uRepository.save(u);
    }

    public void realizarCompra(Long id, int coste){
        Usuario u = uRepository.findById(id).get();
        u.setNumMonedas(u.getNumMonedas() - coste);
        uRepository.save(u);
    }

    public void actualizarUsername(Long id, String username){
        Usuario u = uRepository.findById(id).get();
        u.setUsername(username);
        uRepository.save(u);
    }

    public void actualizarEmail(Long id, String email){
        Usuario u = uRepository.findById(id).get();
        u.setEmail(email);
        uRepository.save(u);
    }

    public int obtenerNumMonedas(Long id) {
        Usuario u = uRepository.findById(id).get();
        return u.getNumMonedas();
    }

    public String obtenerUsername(Long id) {
        Usuario u = uRepository.findById(id).get();
        return u.getUsername();
    }

    public Long obtenerId(String name) {
        Usuario u = uRepository.findByUsername(name);
        return u.getId();
    }
}
