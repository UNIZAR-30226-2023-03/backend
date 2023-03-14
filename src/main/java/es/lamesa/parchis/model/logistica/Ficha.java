package es.lamesa.parchis.model.logistica;

public class Ficha {
    private Color color;

    private int casilla;
    //¿id ó numFicha para ficha para distinguirla de las otras fichas del mismo color?
    //P.e id = ROJO_1, ROJO_2, etc.
    //se les podría asignar un id según el orden en el que salgan de casa.
    Ficha (Color c) {
        color = c;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

enum Color {
	AMARILLO,
    AZUL,
    ROJO,
	VERDE
}
