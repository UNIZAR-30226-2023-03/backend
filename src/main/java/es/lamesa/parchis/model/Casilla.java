package es.lamesa.parchis.model;

import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Data;

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

    //DEBATIR SI SE USA O SE QUITA EL SIGUIENTE ATRIBUTO:
    @Column
    private Color color;

    @OneToMany(mappedBy = "casilla")
    private ArrayList<Ficha> fichas;
 
    @ManyToOne
    @JoinColumn(name = "tablero_id")
    private Tablero tablero;

    public Casilla() {}
    /**
     * Constructor de la casilla.
     * 
     * @param pos La posición de la casilla en el tablero.
     * @param tipo El tipo de la casilla.
     */
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

    public Color getColorPrimeraFicha() {
        return fichas.get(0).getColor();
    }

    public void eliminarPrimeraFicha() {
        fichas.remove(0);
    }

    public void borrarFicha(Ficha f) {
        fichas.remove(f);
    }
}

