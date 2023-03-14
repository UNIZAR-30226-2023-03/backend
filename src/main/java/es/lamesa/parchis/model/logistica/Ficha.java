package es.lamesa.parchis.model.logistica;

public class Ficha {
    private Color color;

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
