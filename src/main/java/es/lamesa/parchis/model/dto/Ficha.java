package es.lamesa.parchis.model.dto;

public class Ficha {
    private Color color;

    Ficha(Color c) {
        color = c;
    }
}

enum Color {
	AMARILLO,
    AZUL,
    ROJO,
	VERDE
}
