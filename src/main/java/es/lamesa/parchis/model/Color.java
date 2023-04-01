package es.lamesa.parchis.model;

public enum Color {
	AMARILLO,
    AZUL,
    ROJO,
	VERDE;

    public Color siguienteTurno(int num_jugadores) {
        return values()[(ordinal() + 1) % num_jugadores];
    }
}
