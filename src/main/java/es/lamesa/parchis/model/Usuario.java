package es.lamesa.parchis.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int numMonedas;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioPartida> partidas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "amistad",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "amigo_id"))
    private List<Usuario> amigos = new ArrayList<>();

    public void encriptarPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

}
