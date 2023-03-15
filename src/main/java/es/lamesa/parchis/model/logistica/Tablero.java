package es.lamesa.parchis.model.logistica;

import java.util.ArrayList;

/**
 * La clase Tablero representa el tablero del juego Parchís.
 */

public class Tablero {
    /**
     * La lista de casillas que forman el perímetro del tablero.
     */
    private ArrayList<Casilla> perimetro;
    /**
     * La lista de casillas que forman el pasillo del tablero.
     */
    private ArrayList<Casilla> pasillo;
    /**
     * Crea una nueva instancia de la clase Tablero, inicializando tanto el perímetro como el pasillo.
     */
    public Tablero() {
        inicializarPerimetro();
        inicializarPasillo();
    }
    
    /**
     * Inicializa la lista de casillas que forman el perímetro del tablero.
     */
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
    /**
     * Inicializa la lista de casillas que forman el pasillo del tablero.
     */
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

    // public int obtenerFichas(int casilla){
    //     for (Casilla c : perimetro){
    //         if(c.getPosicion() == casilla) {
                
    //         }
    //     }
    // }

}
