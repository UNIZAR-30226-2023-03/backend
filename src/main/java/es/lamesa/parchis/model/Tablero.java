package es.lamesa.parchis.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

/**
 * La clase Tablero representa el tablero del juego Parchís.
 */
@Data
@Entity
@Table(name = "tablero")
public class Tablero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "tablero")
    private Partida partida;

    @OneToMany(mappedBy = "tablero")
    private List<Casilla> casillas;

    /**
     * Crea una nueva instancia de la clase Tablero, inicializando tanto el perímetro como el pasillo.
     */
    public Tablero(Partida p) {
        this.partida = p;
        inicializarTablero();
    }

    public void inicializarTablero() {
        //4 pasillos de dimensión 8 -> 4*8 + las [0,67] casillas perimetro
        casillas = new ArrayList<Casilla>(4+68+4*8);
        Casilla c = new Casilla();

        //inicialización de casas
        for(int i = 0; i < 4; i++) {
            c = new Casilla(this, -1, TipoCasilla.CASA, Color.values()[i]);
            // construir fichas
        }

        //inicialización perimetro
        for(int i = 0; i < 68; i++) {
            // CASILLAS SALIDA
            if (i == 4) {
                c = new Casilla(this, i, TipoCasilla.SALIDA, Color.AMARILLO);
            }
            else if (i == 21) {
                c = new Casilla(this, i, TipoCasilla.SALIDA, Color.AZUL);
            }
            else if (i == 37) {
                c = new Casilla(this, i, TipoCasilla.SALIDA, Color.ROJO);
            }
            else if (i == 55) {
                c = new Casilla(this, i, TipoCasilla.SALIDA, Color.VERDE);
            }
            // CASILLAS SEGURO
            else if (i == 11 || i == 28 || i == 45 || i == 62) { // SEGUROS
                c = new Casilla(this, i, TipoCasilla.SEGURO);
            }
            // CASILLAS DE ENTRADA A PASILLO
            else if (i == 16) {
                c = new Casilla(this, i, TipoCasilla.ENTRADA, Color.AZUL);
            }
            else if (i == 33) {
                c = new Casilla(this, i, TipoCasilla.ENTRADA, Color.ROJO);
            }
            else if (i == 50) {
                c = new Casilla(this, i, TipoCasilla.ENTRADA, Color.VERDE);
            }
            else if (i == 67) {
                c = new Casilla(this, i, TipoCasilla.ENTRADA, Color.AMARILLO);
            }
            else {
                c = new Casilla(this, i, TipoCasilla.COMUN);
            }
            casillas.add(c);
        }

        //inicialización pasillos
        for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(this, 68+i, TipoCasilla.META, Color.AMARILLO);
			} 
            else { 
				c = new Casilla(this, 68+i, TipoCasilla.PASILLO, Color.AMARILLO);
			}
			casillas.add(c);
		}
        for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(this, 68+i, TipoCasilla.META, Color.AZUL);
			} 
            else { 
				c = new Casilla(this, 68+i, TipoCasilla.PASILLO, Color.AZUL);
			}
			casillas.add(c);
		}
        for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(this, 68+i, TipoCasilla.META, Color.ROJO);
			} 
            else { 
				c = new Casilla(this, 68+i, TipoCasilla.PASILLO, Color.ROJO);
			}
			casillas.add(c);
		}
        for (int i = 0; i < 8; i++) {
			if (i == 7) {
				c = new Casilla(this, 68+i, TipoCasilla.META, Color.VERDE);
			} 
            else { 
				c = new Casilla(this, 68+i, TipoCasilla.PASILLO, Color.VERDE);
			}
			casillas.add(c);
		}
    }

    public int contarFichas(Color color) {
        int n = 0;
        for(Casilla c : casillas) {
            for (Ficha f : c.getFichas()) {
                if(f.getColor() == color) {
                    n++;
                }
            }
        }
        return n;
    }
    
    public Ficha buscarFicha(int numero_ficha, Color color) {
        for (Casilla c : casillas) {
            for (Ficha f : c.getFichas()) {
                if(f.getNumero() == numero_ficha && f.getColor() == color) {
                    return f;
                }
            }
        }
        return null;
    }

    public int obtenerFichas(int casilla){
        int n_fichas = 0;
        for (Casilla c : casillas){
            if(c.getPosicion() == casilla) {
                n_fichas = c.getFichas().size();
                break;
            }
        }
        return n_fichas;
    }

    public Casilla obtenerCasillaPerimetro(int casilla){
        return casillas.get(casilla);
    }

    public Casilla obtenerCasillaPasillo(int casilla, Color c) {
        if(c == Color.AMARILLO) {
            for (Casilla ca : casillas) {
                if(ca.getPosicion() == casilla && ca.getTipo() == TipoCasilla.PASILLO && ca.getColor() == c) {
                    return ca;
                }
            }
        }
        else if(c == Color.AZUL) {
            for (Casilla ca : casillas) {
                if(ca.getPosicion() == casilla && ca.getTipo() == TipoCasilla.PASILLO && ca.getColor() == c) {
                    return ca;
                }
            }
        }
        else if(c == Color.ROJO) {
            for (Casilla ca : casillas) {
                if(ca.getPosicion() == casilla && ca.getTipo() == TipoCasilla.PASILLO && ca.getColor() == c) {
                    return ca;
                }
            }
        }
        else if(c == Color.VERDE) {
            for (Casilla ca : casillas) {
                if(ca.getPosicion() == casilla && ca.getTipo() == TipoCasilla.PASILLO && ca.getColor() == c) {
                    return ca;
                }
            }
        }
        return null;
    }
}
