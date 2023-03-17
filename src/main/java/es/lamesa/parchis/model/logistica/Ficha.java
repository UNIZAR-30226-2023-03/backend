package es.lamesa.parchis.model.logistica;

public class Ficha {
    private Color color;
    private int id;
    private int casilla;

    public Ficha(Color c, int id, int casilla) {
        color = c;
        this.id = id;
        this.casilla = casilla;
    }

    public Color getColor() {
        return this.color;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCasilla() {
        return this.casilla;
    }

    public void setCasilla(int casilla) {
        this.casilla = casilla;
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



