package es.lamesa.parchis.model.logistica;

import java.util.ArrayList;

import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.dto.Dado;

public class Partida {
    Tablero tablero;
    Dado dado;
    ConfigBarreras config_barreras;
    ArrayList<Usuario> jugadores;
    Usuario jugador_en_turno;
    int i_turno;

    // ¿Fichas de los jugadores?

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
    }

    boolean conectarse(Usuario user) {
        if(jugadores.size() == 4) {
            return false;
        }
        jugadores.add(user);
        return true;
    }

    void empezar() {
        i_turno = 0;
        jugador_en_turno = jugadores.get(i_turno);
    }

    void cambiarTurno() {
        i_turno = (i_turno+1) % 4;
        jugador_en_turno = jugadores.get(i_turno);
    }

    //¿coger el número sacado del dado?
    //¿ficha que un jugador ha escogido mover?
    //etc

}

enum ConfigBarreras {
    SOLO_SEGUROS,
    TODAS_CASILLAS
}
