package es.lamesa.parchis.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.repository.ProductoRepository;
import es.lamesa.parchis.repository.AmistadRepository;
import es.lamesa.parchis.repository.UsuarioEstadisticasRepository;
import es.lamesa.parchis.repository.UsuarioProductoRepository;
import es.lamesa.parchis.model.Amistad;
import es.lamesa.parchis.model.Producto;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioEstadisticas;
import es.lamesa.parchis.model.UsuarioProducto;
import es.lamesa.parchis.model.dto.ResponseAmistad;
import es.lamesa.parchis.model.dto.RequestAmistad;
import es.lamesa.parchis.model.dto.RequestUsuario;
import es.lamesa.parchis.model.dto.ResponseUsuario;
import es.lamesa.parchis.model.dto.RequestProducto;
import es.lamesa.parchis.model.dto.RequestCambio;
import es.lamesa.parchis.model.dto.ResponseEstadisticas;
import es.lamesa.parchis.exception.GenericException;

@Service
public class UsuarioService {

    @Autowired
    ProductoRepository pRepository;

    @Autowired
    UsuarioRepository uRepository;

    @Autowired
    UsuarioProductoRepository upRepository;

    @Autowired
    AmistadRepository aRepository;

    @Autowired
    UsuarioEstadisticasRepository ueRepository;

    @Autowired
    EmailService email;

    public List<Usuario> getUsuarios() {
        return uRepository.findAll();
    }

    public ResponseUsuario addUsuario(RequestUsuario usuario) {
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
        // email.enviarCorreoElectronico(usuario.getEmail());
        UsuarioEstadisticas ue = new UsuarioEstadisticas();
        ue.setUsuario(u);
        u.setEstadisticas(ue);
        // ASIGNAR TABLERO Y FICHAS PREDETERMINADAS AL USUARIO CREADO:
        // 1) obtener tablero predeterminado (en productoRepository según el tipo
        // (Tipo.TABLERO) y el nombre (Tablero Predeterminado)
        // u.getProductos().add(t);
        // 2) obtener ficha predeterminada (en productoRepository según el tipo
        // (Tipo.FICHA) y el nombre (Ficha Predeterminada)
        // u.getProductos().add(f);
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
        } else {
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

    public List<ResponseAmistad> mostrarSolicitudes(Long id) {
        Usuario u = new Usuario();
        u.setId(id);
        List<Usuario> us = aRepository.findSolicitudes(u);
        List<ResponseAmistad> am = new ArrayList<>();
        for (Usuario uu : us) {
            ResponseAmistad a = new ResponseAmistad(uu.getId(), uu.getUsername());
            am.add(a);
        }
        return am;
    }

    public List<ResponseAmistad> getAmigos(Long id) {
        Usuario u = new Usuario();
        u.setId(id);
        List<Amistad> la = aRepository.findAmigos(u);
        List<ResponseAmistad> amigos = new ArrayList<>();
        for (Amistad a : la) {
            ResponseAmistad amigo = new ResponseAmistad(a.getIdAmigo(id), a.getUsernameAmigo(id));
            amigos.add(amigo);
        }
        return amigos;
    }

    public void aceptarSolicitud(RequestAmistad amistad) {
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

    public void eliminarAmigo(RequestAmistad request) {
        Usuario usuario = new Usuario();
        Usuario amigo = new Usuario();
        amigo.setId(request.getAmigo());
        usuario.setId(request.getUsuario());
        Amistad a = aRepository.findAmistad(usuario, amigo);
        aRepository.delete(a);
    }

    public void actualizarMonedas(Long id, int premio) {
        Usuario u = uRepository.findById(id).get();
        u.setNumMonedas(u.getNumMonedas() + premio);
        uRepository.save(u);
    }

    public void actualizarUsername(RequestCambio request) {
        Usuario u = uRepository.findById(request.getId()).get();
        u.setUsername(request.getCambio());
        uRepository.save(u);
    }

    public void actualizarEmail(RequestCambio request) {
        Usuario u = uRepository.findById(request.getId()).get();
        u.setEmail(request.getCambio());
        uRepository.save(u);
    }

    public void actualizarPassword(RequestCambio request) {
        Usuario u = uRepository.findById(request.getId()).get();
        u.setPassword(request.getCambio());
        u.encriptarPassword();
        u = uRepository.save(u);
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

    public List<UsuarioProducto> getProductos(Long id) {
        Usuario u = uRepository.findById(id).get();
        return upRepository.findByUsuario(u);
    }

    public void activarProducto(RequestProducto request) {
        Usuario u = uRepository.findById(request.getUsuario()).get();
        Producto p = pRepository.findById(request.getProducto()).get();
        UsuarioProducto up = upRepository.findByUsuarioAndProducto(u, p);
        Producto activo = upRepository.getProductoActivado(u);
        if (activo.getId() == p.getId()) {
            throw new GenericException("Ya tienes el producto activado");
        }
        up.setActivo(true);
        up = upRepository.findByUsuarioAndProducto(u, activo);
        up.setActivo(false);
        upRepository.save(up);
    }

    public List<ResponseEstadisticas> getRanking() {
        List<UsuarioEstadisticas> ue = ueRepository.findAll();
        List<ResponseEstadisticas> re = new ArrayList<>();
        float mediaComidas = 0;
        float mediaEnMeta = 0;
        String username;
        for (UsuarioEstadisticas e : ue) {
            username = e.getUsuario().getUsername();
            mediaComidas = (float) e.getNumComidas() / (float) e.getPartidasJugadas();
            mediaEnMeta = (float) e.getNumEnMeta() / (float) e.getPartidasJugadas();
            ResponseEstadisticas r = new ResponseEstadisticas(username, e.getPartidasJugadas(), e.getPartidasGanadas(), mediaComidas, mediaEnMeta);
            re.add(r);
        }
        return re;
    }

    public ResponseEstadisticas getEstadisticas(Long id) {
        Usuario u = uRepository.findById(id).get();
        UsuarioEstadisticas ue = ueRepository.findByUsuario(u);
        float mediaComidas = (float) ue.getNumComidas() / (float) ue.getPartidasJugadas();
        float mediaEnMeta = (float) ue.getNumEnMeta() / (float) ue.getPartidasJugadas();
        ResponseEstadisticas re = new ResponseEstadisticas(u.getUsername(), ue.getPartidasJugadas(), ue.getPartidasGanadas(), mediaComidas, mediaEnMeta);
        return re;
    }

}
