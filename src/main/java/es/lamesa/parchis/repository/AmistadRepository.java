package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.Amistad;

import java.util.List;

public interface AmistadRepository extends JpaRepository<Amistad, Long> {
    @Query("SELECT a.usuario FROM Amistad a WHERE a.amigo = :amigo AND a.aceptado = false")
    public List<Usuario> findSolicitudes(@Param("amigo") Usuario amigo);

    public Amistad findByUsuarioAndAmigo(Usuario usuario, Usuario amigo);

    @Query("SELECT a FROM Amistad a WHERE a.usuario = :usuario OR a.amigo = :usuario AND a.aceptado = true")
    public List<Amistad> findAmigos(@Param("usuario") Usuario usuario);

    @Query("SELECT a FROM Amistad a WHERE (a.usuario = :usuario AND a.amigo = :amigo) OR (a.amigo = :usuario AND a.usuario = :amigo)")
    public Amistad findAmistad(@Param("usuario") Usuario usuario, @Param("amigo") Usuario amigo);
}
