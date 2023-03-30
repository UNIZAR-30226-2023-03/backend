package es.lamesa.parchis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.lamesa.parchis.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.username = :login OR u.email = :login")
    Usuario findByUsernameOrEmail(@Param("login") String login);

    Usuario findByUsername(String username);

    Usuario findByEmail(String email);
    
}
