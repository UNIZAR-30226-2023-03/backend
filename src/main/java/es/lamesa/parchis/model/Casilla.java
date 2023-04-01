package es.lamesa.parchis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "casilla")
public class Casilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int posicion;

    @Column(nullable = false)
    private TipoCasilla tipo;

    @Column
    private Color color;

    @OneToMany(mappedBy = "casilla", cascade = CascadeType.ALL)
    private List<Ficha> fichas;
 
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tablero_id")
    private Tablero tablero;

    public Casilla(Tablero tablero, int pos, TipoCasilla tipo) {
        this.posicion = pos;
        if (tipo == TipoCasilla.META){
            fichas = new ArrayList<Ficha>(4);
        }
        else {
            fichas = new ArrayList<Ficha>(2);
        }
        this.tipo = tipo;
        this.tablero = tablero;
    }

    /**
     * Constructor de la casilla.
     * 
     * @param pos La posición de la casilla en el tablero.
     * @param tipo El tipo de la casilla.
     */
    public Casilla(Tablero tablero, int pos, TipoCasilla tipo, Color c) {
        this.posicion = pos;
        if (tipo == TipoCasilla.META){
            fichas = new ArrayList<Ficha>(4);
        }
        else {
            fichas = new ArrayList<Ficha>(2);
        }
        this.tipo = tipo;
        this.tablero = tablero;
        this.color = c;
    }

    public Color obtenerColorPrimeraFicha() {
        return fichas.get(0).getColor();
    }

    public void eliminarPrimeraFicha() {
        fichas.remove(0);
    }

    public void borrarFicha(Ficha f) {
        fichas.remove(f);
    }
}

