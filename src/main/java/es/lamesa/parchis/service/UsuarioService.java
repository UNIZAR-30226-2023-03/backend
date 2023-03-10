package es.lamesa.parchis.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
        return repository.save(usuario);   
    }
}
