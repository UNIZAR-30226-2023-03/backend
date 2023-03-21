package es.lamesa.parchis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.dto.UsuarioDto;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository repository;
    
    public List<Usuario> getUsuarios() {
        return repository.findAll();
    }

    public Usuario addUsuario(UsuarioDto usuario) {
        Usuario u = new Usuario();
        u.setEmail(usuario.getEmail());
        u.setUsername(usuario.getUsername());
        u.setPassword(usuario.getPassword());
        u.encriptarPassword();
        return repository.save(u);
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
