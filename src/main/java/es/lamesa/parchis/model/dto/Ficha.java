package es.lamesa.parchis.model.dto;

public class Ficha {
    private Color color;
    //¿id ó numFicha para ficha para distinguirla de las otras fichas del mismo color?
    //P.e id = ROJO_1, ROJO_2, etc.
    //se les podría asignar un id según el orden en el que salgan de casa.

    Ficha(Color c) {
        color = c;
    }

    public Color getColor() {
        return color;
    }
}

enum Color {
	AMARILLO,
    AZUL,
    ROJO,
	VERDE
}
