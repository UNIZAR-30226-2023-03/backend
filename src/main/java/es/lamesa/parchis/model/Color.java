package es.lamesa.parchis.model;

public enum Color {
	AMARILLO,
    AZUL,
    ROJO,
	VERDE;

    public Color siguienteTurno() {
        return values()[(ordinal() + 1) % values().length];
    }
}
