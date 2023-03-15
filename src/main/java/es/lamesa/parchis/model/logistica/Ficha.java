package es.lamesa.parchis.model.logistica;

public class Ficha {
    private Color color;
    private int id;
    private int casilla;
    
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
	VERDE;

    public Color siguienteTurno() {
        return values()[(ordinal() + 1) % values().length];
    }
}



