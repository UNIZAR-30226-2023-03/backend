package es.lamesa.parchis.model.logistica;

import java.util.ArrayList;
import java.util.HashMap;

import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.dto.Dado;
import es.lamesa.parchis.model.logistica.Ficha;

public class Partida {
    Tablero tablero;
    Dado dado;
    ConfigBarreras config_barreras;
    ArrayList<Usuario> jugadores;
    HashMap<Usuario,Color> colores_jugadores;
    Color turno;

    ArrayList<Ficha> amarillas;
    ArrayList<Ficha> azules;
    ArrayList<Ficha> rojas;
    ArrayList<Ficha> verdes;

    Partida(Usuario anfitrion, String configBarreras) {
        jugadores = new ArrayList<Usuario>(4);
        jugadores.add(anfitrion);
        if(configBarreras.contentEquals("Solo en seguros")) {
            config_barreras = ConfigBarreras.SOLO_SEGUROS;
        }
        else if(configBarreras.contentEquals("En todas las casillas")) {
            config_barreras = ConfigBarreras.TODAS_CASILLAS;
        }
        dado = new Dado();
        tablero = new Tablero();

        amarillas = new ArrayList<Ficha>(4);
        azules = new ArrayList<Ficha>(4);
        rojas = new ArrayList<Ficha>(4);
        verdes = new ArrayList<Ficha>(4);
    }

    boolean conectarse(Usuario user) {
        if(jugadores.size() == 4) {
            return false;
        }
        jugadores.add(user);
        return true;
    }

    void empezar() {
        // int n = random(0,3);
        turno = Color.ROJO;
    }

    void cambiarTurno() {
        turno.siguienteTurno();
    }

    void comprobarMovimientos(int num_dado) {
        if(turno == Color.AMARILLO) {

        }
        else if(turno == Color.AZUL){
            
        }
    }

    void realizarMovimiento(int id_ficha) {
        //buscar ficha where color=turno AND id=id_ficha
    }

    //¿coger el número sacado del dado?
    //¿ficha que un jugador ha escogido mover?
    //etc

}

enum ConfigBarreras {
    SOLO_SEGUROS,
    TODAS_CASILLAS
}
