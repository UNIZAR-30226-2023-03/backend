package es.lamesa.parchis.model.dto;

import java.util.ArrayList;

import es.lamesa.parchis.model.Usuario;

public class ConfigPartida {
    Tablero tablero;
    Dado dado;
    ConfigBarreras config_barreras;
    ArrayList<Usuario> jugadores;

    ConfigPartida(Usuario anfitrion, String configBarreras) {
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

}

enum ConfigBarreras {
    SOLO_SEGUROS,
    TODAS_CASILLAS
}
