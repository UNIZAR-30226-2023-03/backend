package es.lamesa.parchis.model.dto;

import java.util.ArrayList;

public class Casilla {
    private int posicion;
    private ArrayList<Ficha> fichas;
    private TipoCasilla tipo;

    public Casilla(int pos, TipoCasilla tipo) {
        this.posicion = pos;
        fichas = new ArrayList<Ficha>(2);
        this.tipo = tipo;
    }
}

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
