package es.lamesa.parchis.model.logistica;

import java.util.ArrayList;

/**
 * Clase que representa una casilla del tablero de parchís.
 */
public class Casilla {
    /**
     * Posición de la casilla en el tablero.
     */
    private int posicion;
    /**
     * Lista de fichas en la casilla.
     */
    private ArrayList<Ficha> fichas;
    /**
     * Tipo de la casilla.
     */
    private TipoCasilla tipo;
    /**
     * Constructor de la casilla.
     * 
     * @param pos La posición de la casilla en el tablero.
     * @param tipo El tipo de la casilla.
     */
    public Casilla(int pos, TipoCasilla tipo) {
        this.posicion = pos;
        fichas = new ArrayList<Ficha>(2);
        this.tipo = tipo;
    }

    public int getPosicion() {
        return posicion;
    }

    public TipoCasilla getTipoCasilla() {
        return tipo;
    }

    public void anyadirFicha(Ficha f) {
        fichas.add(f);
    }
}
/**
 * Enumeración que define los tipos de casillas posibles en el tablero de parchís.
 */
enum TipoCasilla {
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
