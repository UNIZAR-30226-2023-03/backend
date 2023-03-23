package es.lamesa.parchis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.repository.AmistadRepository;
import es.lamesa.parchis.model.Amistad;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.dto.RequestAmistad;
import es.lamesa.parchis.model.dto.UsuarioDto;
import es.lamesa.parchis.model.dto.RequestRespuestaAmistad;


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
            return false;
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
        a.setUsuario(usuario);
        usuario.setId(amistad.getUsuario());
        a.setUsuario(usuario);                                   
        a.setAceptado(false);
        usuario.getAmigos().add(amigo); 
        uRepository.save(usuario);            
        return true;
    }

    public List<Usuario> mostrarSolicitudes(Long id){
        return aRepository.findByAmigoAndAceptado(id,false);
    }

    public void setSolicitudes(RequestRespuestaAmistad p){
        
    }
}
