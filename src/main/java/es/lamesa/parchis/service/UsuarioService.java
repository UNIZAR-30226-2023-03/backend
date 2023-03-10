package es.lamesa.parchis.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.model.Usuario;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository repository;
    
    public ArrayList<Usuario> getUsuarios() {
        return (ArrayList<Usuario>) repository.findAll();
    }

    public Usuario addUsuario(Usuario usuario) {
        usuario.encriptarPassword();
        return repository.save(usuario);
    }

    public boolean validarUsuario(String login, String password) {
        Usuario usuario = repository.findByUsernameOrEmail(login);
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
        repository.deleteById(id);
    }

}
