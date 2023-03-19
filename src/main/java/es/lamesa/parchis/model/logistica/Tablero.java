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
    private ArrayList<Casilla> pasillo_amarillo;
    private ArrayList<Casilla> pasillo_azul;
    private ArrayList<Casilla> pasillo_rojo;
    private ArrayList<Casilla> pasillo_verde;
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
		pasillo_amarillo = new ArrayList<Casilla>(8);
        pasillo_azul = new ArrayList<Casilla>(8);
        pasillo_rojo = new ArrayList<Casilla>(8);
        pasillo_verde = new ArrayList<Casilla>(8);
        Casilla c;
		for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(68+i, TipoCasilla.META);
			} 
            else { 
				c = new Casilla(68+i, TipoCasilla.PASILLO);
			}
			pasillo_amarillo.add(c);
		}
        for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(68+i, TipoCasilla.META);
			} 
            else { 
				c = new Casilla(68+i, TipoCasilla.PASILLO);
			}
			pasillo_azul.add(c);
		}
        for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(68+i, TipoCasilla.META);
			} 
            else { 
				c = new Casilla(68+i, TipoCasilla.PASILLO);
			}
			pasillo_rojo.add(c);
		}
        for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(68+i, TipoCasilla.META);
			} 
            else { 
				c = new Casilla(68+i, TipoCasilla.PASILLO);
			}
			pasillo_verde.add(c);
		}
        //pasillo_azul = new ArrayList<Casilla>(pasillo_amarillo);
        //pasillo_rojo = new ArrayList<Casilla>(pasillo_amarillo);
        //pasillo_verde = new ArrayList<Casilla>(pasillo_amarillo);
	}

    public int obtenerFichas(int casilla){
        int n_fichas = 0;
        for (Casilla c : perimetro){
            if(c.getPosicion() == casilla) {
                n_fichas = c.getFichas().size();
                break;
            }
        }
        return n_fichas;
    }

    public Casilla obtenerCasilla(int casilla){
        return perimetro.get(casilla);
    }

    public Casilla obtenerCasillaPasillo(int casilla, Color c) {
        Casilla fallo = new Casilla();
        if(c == Color.AMARILLO) {
            return pasillo_amarillo.get(casilla);
        }
        else if(c == Color.AZUL) {
            return pasillo_azul.get(casilla);
        }
        else if(c == Color.ROJO) {
            return pasillo_rojo.get(casilla);
        }
        else if(c == Color.VERDE) {
            return pasillo_verde.get(casilla);
        }
        return fallo;
    }
}
