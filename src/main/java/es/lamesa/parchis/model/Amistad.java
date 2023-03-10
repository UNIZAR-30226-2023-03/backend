package es.lamesa.parchis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "amistad")
public class Amistad {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "usuario1", referencedColumnName = "id")
    private Usuario usuario1;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "usuario2", referencedColumnName = "id")
    private Usuario usuario2;
    

    public Usuario getUsuario1() {
        return this.usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Usuario getUsuario2() {
        return this.usuario2;
    }

    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }
    
}
