package es.lamesa.parchis.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.lamesa.parchis.repository.UsuarioRepository;
import es.lamesa.parchis.security.TokenUtil;
import es.lamesa.parchis.repository.ProductoRepository;
import es.lamesa.parchis.repository.AmistadRepository;
import es.lamesa.parchis.repository.PartidaRepository;
import es.lamesa.parchis.repository.UsuarioEstadisticasRepository;
import es.lamesa.parchis.repository.UsuarioProductoRepository;
import es.lamesa.parchis.repository.UsuarioPartidaRepository;
import es.lamesa.parchis.model.Amistad;
import es.lamesa.parchis.model.EstadoPartida;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.Producto;
import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.UsuarioEstadisticas;
import es.lamesa.parchis.model.UsuarioProducto;
import es.lamesa.parchis.model.TipoEmail;
import es.lamesa.parchis.model.TipoProducto;
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
    PartidaRepository paRepository;

    @Autowired
    UsuarioRepository uRepository;

    @Autowired
    UsuarioProductoRepository upRepository;

    @Autowired
    UsuarioPartidaRepository upaRepository;

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
        // email.enviarCorreoElectronico(usuario.getEmail(), u.getUsername(), TipoEmail.REGISTRO);
        UsuarioEstadisticas ue = new UsuarioEstadisticas();
        ue.setUsuario(u);
        u.setEstadisticas(ue);
        // ASIGNAR TABLERO Y FICHAS PREDETERMINADAS AL USUARIO CREADO (hecho, solo descomentar):
        // Producto p = pRepository.findByNombre("Tablero Predeterminado");
        // UsuarioProducto upt = new UsuarioProducto(u,p,true);
        // u.getProductos().add(upt);
        // p = pRepository.findByNombre("Ficha Predeterminada");
        // UsuarioProducto upf = new UsuarioProducto(u,p,true);
        // u.getProductos().add(upf);
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
            ResponseAmistad a = new ResponseAmistad(uu.getId(), uu.getUsername(), null, null);
            am.add(a);
        }
        return am;
    }

    public List<ResponseAmistad> getAmigos(Long id) {
        Usuario u = new Usuario();
        u.setId(id);
        List<Amistad> la = aRepository.findAmigos(u);
        List<ResponseAmistad> amigos = new ArrayList<>();
        Long idPartida = null;
        EstadoPartida estado = null;
        for (Amistad a : la) {
            if (upaRepository.estaJugando(u)) {
                idPartida = upaRepository.getPartida(u);
                if (idPartida != null) {
                    estado = EstadoPartida.ESPERANDO_JUGADORES;
                    Partida p = paRepository.findById(idPartida).get();
                    if (p.getJugadores().size() == 4) {
                        idPartida = null;
                    }
                }
                else {
                    estado = EstadoPartida.EN_PROGRESO;
                }
            }
            ResponseAmistad amigo = new ResponseAmistad(a.getIdAmigo(id), a.getUsernameAmigo(id), estado, idPartida);
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

    public void actualizarUsername(RequestCambio request) {
        Usuario u = uRepository.findById(request.getId()).get();
        u.setUsername(request.getCambio());
        // email.enviarCorreoElectronico(usuario.getEmail(), u.getUsername(), TipoEmail.CAMBIO_USERNAME);
        uRepository.save(u);
    }

    public void actualizarEmail(RequestCambio request) {
        Usuario u = uRepository.findById(request.getId()).get();
        u.setEmail(request.getCambio());
        // email.enviarCorreoElectronico(usuario.getEmail(), u.getUsername(), TipoEmail.CAMBIO_EMAIL);
        uRepository.save(u);
    }

    public void actualizarPassword(RequestCambio request) {
        Usuario u = uRepository.findById(request.getId()).get();
        u.setPassword(request.getCambio());
        u.encriptarPassword();
        // email.enviarCorreoElectronico(usuario.getEmail(), u.getUsername(), TipoEmail.CAMBIO_PASSWORD);
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

    public List<Producto> getProductos(Long id) {
        Usuario u = uRepository.findById(id).get();
        return upRepository.findByUsuario(u);
    }

    public void activarProducto(RequestProducto request) {
        Usuario u = uRepository.findById(request.getUsuario()).get();
        Producto p = pRepository.findById(request.getProducto()).get();
        UsuarioProducto up = upRepository.findByUsuarioAndProducto(u, p);
        Producto activo = upRepository.getProductoActivado(u, p.getTipoProducto());
        if (activo.getId() == p.getId()) {
            throw new GenericException("Ya tienes el producto activado");
        }
        up.setActivo(true);
        up = upRepository.findByUsuarioAndProducto(u, activo);
        up.setActivo(false);
        upRepository.save(up);
    }

    public Producto getFichaActiva(Long id) {
        Usuario u = uRepository.findById(id).get();
        return upRepository.getProductoActivado(u, TipoProducto.FICHA);
    }

    public Producto getTableroActivo(Long id) {
        Usuario u = uRepository.findById(id).get();
        return upRepository.getProductoActivado(u, TipoProducto.TABLERO);
    }

    public List<ResponseEstadisticas> getRanking(String campo) {
        List<UsuarioEstadisticas> ue = new ArrayList<>();
        if (campo.contentEquals("partidasJugadas")) {
            ue = ueRepository.findAllByOrderByPartidasJugadasDesc();
        }
        else if (campo.contentEquals("partidasGanadas")) {
            ue = ueRepository.findAllByOrderByPartidasGanadasDesc();
        }
        else if (campo.contentEquals("mediaComidas")) {
            ue = ueRepository.findAllByOrderByNumComidasDesc();
        }
        else if (campo.contentEquals("mediaEnMeta")) {
            ue = ueRepository.findAllByOrderByNumEnMetaDesc();
        }
        else if (campo.contentEquals("torneosJugados")) {
            ue = ueRepository.findAllByOrderByTorneosJugadosDesc();
        }
        else if (campo.contentEquals("torneosGanados")) {
            ue = ueRepository.findAllByOrderByTorneosGanadosDesc();
        }
        
        List<ResponseEstadisticas> re = new ArrayList<>();
        float mediaComidas = 0.0f;
        float mediaEnMeta = 0.0f;
        String username;
        for (UsuarioEstadisticas e : ue) {
            username = e.getUsuario().getUsername();        
            if (e.getPartidasJugadas() != 0) {
                mediaComidas = (float) e.getNumComidas() / (float) e.getPartidasJugadas();
                mediaEnMeta = (float) e.getNumEnMeta() / (float) e.getPartidasJugadas();
            }
            ResponseEstadisticas r = new ResponseEstadisticas(username, e.getPartidasJugadas(), e.getPartidasGanadas(), mediaComidas, mediaEnMeta, e.getTorneosJugados(), e.getTorneosGanados());
            re.add(r);
        }
        return re;
    }

    public ResponseEstadisticas getEstadisticas(Long id) {
        Usuario u = uRepository.findById(id).get();
        UsuarioEstadisticas ue = ueRepository.findByUsuario(u);
        float mediaComidas = 0.0f;
        float mediaEnMeta = 0.0f;
        if (ue.getPartidasJugadas() != 0) {
            mediaComidas = (float) ue.getNumComidas() / (float) ue.getPartidasJugadas();
            mediaEnMeta = (float) ue.getNumEnMeta() / (float) ue.getPartidasJugadas();
        }
        ResponseEstadisticas re = new ResponseEstadisticas(u.getUsername(), ue.getPartidasJugadas(), ue.getPartidasGanadas(), mediaComidas, mediaEnMeta, ue.getTorneosJugados(), ue.getTorneosGanados());
        return re;
    }

    public void recuperarPassword(String em) {
        Usuario u = uRepository.findByEmail(em);
        if (u == null){
            throw new GenericException("No existe un usuario con este correo");
        }
        String token = TokenUtil.generateToken(u.getId(), em);
        email.enviarCorreoRecuperacion(em, u.getUsername(), token);
    }
}
