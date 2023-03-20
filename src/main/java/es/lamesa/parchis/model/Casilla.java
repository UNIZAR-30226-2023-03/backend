package es.lamesa.parchis.model;

import jakarta.persistence.*;
import java.util.ArrayList;
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

    @Column
    private Color color;

    @OneToMany(mappedBy = "casilla")
    private ArrayList<Ficha> fichas;
 
    @ManyToOne
    @JoinColumn(name = "tablero_id")
    private Tablero tablero;

//     public Casilla() {}
//     /**
//      * Constructor de la casilla.
//      * 
//      * @param pos La posición de la casilla en el tablero.
//      * @param tipo El tipo de la casilla.
//      */
//     public Casilla(int pos, TipoCasilla tipo) {
//         this.posicion = pos;
//         if (tipo == TipoCasilla.META){
//             fichas = new ArrayList<Ficha>(4);
//         }
//         else {
//             fichas = new ArrayList<Ficha>(2);
//         }
//         this.tipo = tipo;
//     }

//     public Color getColorPrimeraFicha() {
//         return fichas.get(0).getColor();
//     }

//     public void eliminarPrimeraFicha() {
//         fichas.remove(0);
//     }

//     public void borrarFicha(Ficha f) {
//         fichas.remove(f);
//     }
}
// /**
//  * Enumeración que define los tipos de casillas posibles en el tablero de parchís.
//  */
enum TipoCasilla {
    CASA,
    COMUN,
    SALIDA_AZUL,
    SALIDA_AMARILLO,
    SALIDA_ROJO,
    SALIDA_VERDE,
	SEGURO,
	PASILLO,
    ENTRADA_AZUL,
    ENTRADA_AMARILLO,
    ENTRADA_ROJO,
    ENTRADA_VERDE,
	META
}
