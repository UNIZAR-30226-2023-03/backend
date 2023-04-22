package es.lamesa.parchis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase Tablero representa el tablero del juego Parchís.
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "tablero")
public class Tablero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;

    @OneToMany(mappedBy = "tablero", cascade = CascadeType.ALL)
    private List<Casilla> casillas = new ArrayList<>();

    /**
     * Crea una nueva instancia de la clase Tablero, inicializando tanto el perímetro como el pasillo.
     */
    public Tablero(int num_jugadores, Partida p) {
        this.partida = p;
        inicializarTablero(num_jugadores);
    }

    public void inicializarTablero(int num_jugadores) {
        //4 pasillos de dimensión 8 -> 4*8 + las [0,67] casillas perimetro
        casillas = new ArrayList<Casilla>(num_jugadores + 68 + num_jugadores * 8);
        Casilla c = new Casilla();
        Ficha f;        

        //inicialización perimetro
        for (int i = 0; i < 68; i++) {
            // CASILLAS SALIDA
            if (i == 4) {
                c = new Casilla(this, i, TipoCasilla.SALIDA, Color.AMARILLO);
            }
            else if (i == 21) {
                c = new Casilla(this, i, TipoCasilla.SALIDA, Color.AZUL);
            }
            else if (i == 38) {
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

        for (int i = 0; i < num_jugadores; i++) {
            for (int j = 0; j < 8; j++) {
                if (j == 7) {
                    c = new Casilla(this, 68 + j, TipoCasilla.META, Color.values()[i]);
                }
                else {
                    c = new Casilla(this, 68 + j, TipoCasilla.PASILLO, Color.values()[i]);
                }
                casillas.add(c);
            }
        }
        
        //inicialización de casas
        for (int i = 0; i < num_jugadores; i++) {
            c = new Casilla(this, -1, TipoCasilla.CASA, Color.values()[i]);
            casillas.add(c);
            // poner las fichas en las casillas CASA:
            int num_fichas = 4;
            if (partida.getConfigFichas() == ConfigFichas.RAPIDO){
                num_fichas = 2;
            }
            for (int j = 1; j <= num_fichas; j++) {
                f = new Ficha(Color.values()[i], j, c);
                c.getFichas().add(f);
            }
        }
    }

    public int contarFichas(Color color) {
        int n = 0;
        for(Casilla c : casillas) {
            if (c.getTipo() != TipoCasilla.CASA) {
                for (Ficha f : c.getFichas()) {
                    if (f.getColor() == color) {
                        n++;
                    }
                }
            }
        }
        return n;
    }
    
    public Ficha buscarFicha(int numero_ficha, Color color) {
        for (Casilla c : casillas) {
            for (Ficha f : c.getFichas()) {
                if (f.getNumero() == numero_ficha && f.getColor() == color) {
                    return f;
                }
            }
        }
        return null;
    }

    public int obtenerFichas(int casilla){
        int n_fichas = 0;
        for (Casilla c : casillas){
            if (c.getPosicion() == casilla) {
                n_fichas = c.getFichas().size();
                break;
            }
        }
        return n_fichas;
    }

    public int obtenerFichasColor(int casilla, Color color) {
        int n_fichas = 0;
        for (Casilla c : casillas) {
            if (c.getPosicion() == casilla) {
                for (Ficha f : c.getFichas()) {
                    if (f.getColor() == color) {
                        n_fichas++;
                    }
                }
            }
        }
        return n_fichas;
    }

    public int obtenerSalida(Color c) {
        int num_casilla = -2;
        if (c == Color.AMARILLO) {
            num_casilla = 4;
        }
        else if (c == Color.AZUL){
            num_casilla = 21;
        }
        else if (c == Color.ROJO){
            num_casilla = 38;
        }
        else if (c == Color.VERDE){
            num_casilla = 55;
        }
        return num_casilla;
    }

    public Casilla obtenerCasillaCasa(Color c){
        for (Casilla ca : casillas) {
            if(ca.getColor() == c && ca.getPosicion() == -1) {
                return ca;
            }
        }
        return null;
    }

    public Casilla obtenerCasillaPerimetro(int casilla){
        return casillas.get(casilla);
    }

    public Casilla obtenerCasillaPasillo(int casilla, Color c) {
        for (Casilla ca : casillas) {
            if (ca.getPosicion() == casilla && (ca.getTipo() == TipoCasilla.PASILLO || ca.getTipo() == TipoCasilla.META) && ca.getColor() == c) {
                return ca;
            }
        }
        return null;
    }

    public List<Ficha> obtenerFichasCasa(Color c){
        List<Ficha> fichas = new ArrayList<>();
        for (Casilla ca : casillas){
            if (ca.getTipo() == TipoCasilla.CASA && ca.getColor() == c){
                for (Ficha f : ca.getFichas()) {
                    fichas.add(f);
                }
            }
        }
        return fichas;
    }

    public List<Ficha> obtenerFichasMeta(Color c){
        List<Ficha> fichas = new ArrayList<>();
        for (Casilla ca : casillas){
            if (ca.getTipo() == TipoCasilla.META && ca.getColor() == c){
                for (Ficha f : ca.getFichas()) {
                    fichas.add(f);
                }
            }
        }
        return fichas;
    }

    public List<Ficha> obtenerFichasTablero(Color c){
        List<Ficha> fichas = new ArrayList<>();
        for (Casilla ca : casillas) {
            if ((ca.getTipo() != TipoCasilla.META && ca.getTipo() != TipoCasilla.CASA) || (ca.getTipo() == TipoCasilla.PASILLO && ca.getColor() == c)) {
                for (Ficha f : ca.getFichas()) {
                    if (f.getColor() == c) {
                        fichas.add(f);
                    }
                }
            }
        }
        return fichas;
    }

    public Ficha obtenerFichaMasAvanzada(Color c) {
        Ficha f = null;
        for (Casilla ca : casillas) {
            if (ca.getTipo() != TipoCasilla.META && ca.getTipo() != TipoCasilla.PASILLO && ca.getTipo() != TipoCasilla.CASA) {
                for (Ficha i : ca.getFichas()) {
                    if (i.getColor() == c) {
                        f = i;
                    }
                }
            }
        }
        return f;    
    } 

    public Ficha ObtenerFichaBarrera(Color c) {
        Ficha f = null;
        for (Casilla ca : casillas) {
            if (ca.getFichas().size() == 2) {
                List<Ficha> fichs = ca.getFichas();
                if (fichs.get(0).getColor() == c && fichs.get(1).getColor() == c) {
                    return fichs.get(0);
                }
            }
        }
        return f;
    }
}
