package es.lamesa.parchis.model.logistica;

import java.util.ArrayList;
import java.util.HashMap;

import es.lamesa.parchis.model.Usuario;
import es.lamesa.parchis.model.dto.Dado;

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

    public void sacarFicha(int id_ficha){
        Ficha f = null;
        int id_casilla = 0;
        if (turno == Color.AMARILLO) {
            id_casilla = 4;
            f = new Ficha(turno, id_ficha, id_casilla);
            amarillas.add(f);
        }
        else if (turno == Color.AZUL){
            id_casilla = 21;
            f = new Ficha(turno, id_ficha, id_casilla);
            azules.add(f);
        }
        else if (turno == Color.ROJO){
            id_casilla = 38;
            f = new Ficha(turno, id_ficha, id_casilla);
            rojas.add(f);
        }
        else if (turno == Color.VERDE){
            id_casilla = 55;
            f = new Ficha(turno, id_ficha, id_casilla);
            verdes.add(f);
        }
        Casilla c = tablero.obtenerCasilla(id_casilla); 
        c.anyadirFicha(f);
    }

    public void comprobarMovimientos(int num_dado) {
        int id_casilla;
        dado.setNum(num_dado);
        ArrayList<Ficha> bloqueadas = new ArrayList<Ficha>();
        ArrayList<Ficha> fichas_del_turno = new ArrayList<Ficha>();
        if (turno == Color.AMARILLO) {
            fichas_del_turno = amarillas;
        }
        else if (turno == Color.AZUL){
            fichas_del_turno = azules;
        }
        else if (turno == Color.ROJO){
            fichas_del_turno = rojas;
        }
        else if (turno == Color.VERDE){
            fichas_del_turno = verdes;
        }
        if (fichas_del_turno.size() == 4){
            num_dado++;
        }
        if (num_dado == 5 && fichas_del_turno.size() < 4){
            sacarFicha(fichas_del_turno.size() + 1);
        }
        else {
            for(Ficha i : fichas_del_turno) {
                id_casilla = i.getCasilla();
                Casilla c = tablero.obtenerCasilla(id_casilla);
                if (c.getTipoCasilla() == TipoCasilla.PASILLO){
                    if (c.getPosicion() + num_dado > 75){
                        bloqueadas.add(i);   
                    }
                }
                else{
                    if(tablero.obtenerFichas(id_casilla + num_dado) == 2) {
                        bloqueadas.add(i);
                    }
                    else { 
                        for(int j = id_casilla + 1; j < id_casilla+num_dado; j++) {
                            if (tablero.obtenerFichas(j) == 2) {
                                /*CONFIG.BLOQUEANTE_SOLO_SEGURO and TIPOCASILLA.SEGURO 
                                * OR CONFIG.BLOQUEANTE_TODO -> bloquea ficha
                                */
                                if ((c.getTipoCasilla() == TipoCasilla.SEGURO &&
                                config_barreras == ConfigBarreras.SOLO_SEGUROS) ||
                                config_barreras == ConfigBarreras.TODAS_CASILLAS){
                                    bloqueadas.add(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void realizarMovimiento(int id_ficha) {
        Ficha f = null;
        int id_casilla_prepasillo = 0;
        if (turno == Color.AMARILLO) {
            f = amarillas.get(id_ficha);
            id_casilla_prepasillo = 67;
        }
        else if (turno == Color.AZUL){
            f = azules.get(id_ficha);
            id_casilla_prepasillo = 16;
        }
        else if (turno == Color.ROJO){
            f = rojas.get(id_ficha);
            id_casilla_prepasillo = 33;
        }
        else if (turno == Color.VERDE){
            f = verdes.get(id_ficha);
            id_casilla_prepasillo = 50;
        }
        int id_casilla = f.getCasilla();
        if (id_casilla + dado.getNum() >= id_casilla_prepasillo){
            //entrada a pasillo
            id_casilla = 67 + ((id_casilla + dado.getNum() - id_casilla_prepasillo));
            f.setCasilla(id_casilla);
            Casilla c = tablero.obtenerCasillaPasillo(id_casilla, turno);
            c.anyadirFicha(f);
        } 
        else if (id_casilla > 67){
            Casilla c = tablero.obtenerCasillaPasillo(id_casilla, turno);
            c.borrarFicha(f);
            id_casilla = id_casilla + dado.getNum();
            f.setCasilla(id_casilla);
            c = tablero.obtenerCasillaPasillo(id_casilla, turno);
            c.anyadirFicha(f);
            /*comprobar si han llegado la ficha a meta. Si sí, dos cosas
             * 1. Enviar al frontend para elegir ficha con la q mover
             *      10 posiciones.
             * 2. Si han llegado las 4 fichas, indicar fin de partida
            */
        }
        else {
            f.setCasilla(id_casilla+dado.getNum()); // actualizamos el atributo posición de la clase Ficha
            Casilla c = tablero.obtenerCasilla(id_casilla); 
            c.borrarFicha(f);
            c = tablero.obtenerCasilla((id_casilla + dado.getNum()));
            if(c.getFichas().size() == 1 &&
                c.getColorPrimeraFicha() != turno && 
                c.getTipoCasilla() == TipoCasilla.COMUN) {
                c.eliminarPrimeraFicha();
                /* AVANZAR 20 CASILLAS (AVISAR FRONTEND) 
                 * Para ello, hacer otra vez el comprobarMovimientos(20)
                */
            }
            c.anyadirFicha(f);
        }
        
    }
}

enum ConfigBarreras {
    SOLO_SEGUROS,
    TODAS_CASILLAS
}
