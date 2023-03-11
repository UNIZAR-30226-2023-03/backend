package es.lamesa.parchis.model.dto;

import es.lamesa.parchis.model.dto.Casilla;

import java.util.ArrayList;

public class Tablero {
    private ArrayList<Casilla> perimetro;
    private ArrayList<Casilla> pasillo;

    public Tablero() {
        inicializarPerimetro();
        inicializarPasillo();
    }

    public void inicializarPerimetro() {
        perimetro = new ArrayList<Casilla>(68);
        Casilla c;
        for (int i = 0; i < 68; i++) {
            // CASILLAS SALIDA
            if (i == 4) {
                c = new Casilla(i, TipoCasilla.SALIDA_AMARILLO);
            }
            else if (i == 21) {
                c = new Casilla(i, TipoCasilla.SALIDA_AZUL);
            }
            else if (i == 37) {
                c = new Casilla(i, TipoCasilla.SALIDA_ROJO);
            }
            else if (i == 55) {
                c = new Casilla(i, TipoCasilla.SALIDA_VERDE);
            }
            // CASILLAS SEGURO
            else if (i == 11 || i == 28 || i == 45 || i == 62) { // SEGUROS
                c = new Casilla(i, TipoCasilla.SEGURO);
            }
            // CASILLAS DE ENTRADA A PASILLO
            else if (i == 16) {
                c = new Casilla(i, TipoCasilla.ENTRADA_AZUL);
            }
            else if (i == 33) {
                c = new Casilla(i, TipoCasilla.ENTRADA_ROJO);
            }
            else if (i == 50) {
                c = new Casilla(i, TipoCasilla.ENTRADA_VERDE);
            }
            else if (i == 67) {
                c = new Casilla(i, TipoCasilla.ENTRADA_AMARILLO);
            }
            else {
                c = new Casilla(i, TipoCasilla.COMUN);
            }
            perimetro.add(c);
        }
    }
    
    public void inicializarPasillo() {
		pasillo = new ArrayList<Casilla>(8);
        Casilla c;
		for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(i, TipoCasilla.META);
			} 
            else { 
				c = new Casilla(i, TipoCasilla.PASILLO);
			}
			pasillo.add(c);
		}
	}

}
