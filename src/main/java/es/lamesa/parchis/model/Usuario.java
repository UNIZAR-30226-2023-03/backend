package es.lamesa.parchis.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int numMonedas;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioPartida> partidas = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Amistad> solicitudesEnviadas = new ArrayList<>();
    
    @JsonManagedReference
    @OneToMany(mappedBy = "amigo", cascade = CascadeType.ALL)
    private List<Amistad> solicitudesRecibidas = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioProducto> productos = new ArrayList<>();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private UsuarioEstadisticas estadisticas;

    public void encriptarPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

}
