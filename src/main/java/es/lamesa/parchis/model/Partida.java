package es.lamesa.parchis.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@NoArgsConstructor
@Data
@Entity
@Table(name = "partida")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
    private List<UsuarioPartida> jugadores = new ArrayList<>(4);

    @OneToOne
    @JoinColumn(name = "tablero_id")
    private Tablero tablero; //una vez finalizada la partida, se podría borrar?

    @Column
    private Color turno;

    @Column
    private String nombre;

    @Column
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConfigBarreras configBarreras;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPartida estado;

    public Partida(String nombre, String password, UsuarioPartida jugador, ConfigBarreras configBarreras, EstadoPartida estado) {
        this.nombre = nombre;
        this.password = password;
        this.jugadores.add(jugador);
        this.configBarreras = configBarreras;
        this.estado = estado;
        this.tablero = new Tablero(this);
    }

    void empezar() {
        int n = (int) (Math.random() * 4); //se elije de manera aleatoria el jugador que empieza en el intervalo [0,3]
        turno = Color.values()[n];
    }

    void cambiarTurno() {
        turno.siguienteTurno();
    }

    public void sacarFicha(int id_ficha){
        Ficha f = null;
        int id_casilla = 0;
        if (turno == Color.AMARILLO) {
            id_casilla = 4;
        }
        else if (turno == Color.AZUL){
            id_casilla = 21;
        }
        else if (turno == Color.ROJO){
            id_casilla = 38;
        }
        else if (turno == Color.VERDE){
            id_casilla = 55;
        }
        Casilla c = tablero.getCasillas().get(id_casilla);
        f = new Ficha(turno, id_ficha, c);
        c.getFichas().add(f);
    }

    public void comprobarMovimientos(int num_dado) {
        int id_casilla;
        List<Ficha> bloqueadas = new ArrayList<Ficha>();
        List<Ficha> fichas_del_turno = new ArrayList<Ficha>();
        Ficha f = null;
        int n = tablero.contarFichas(turno);
        for(int i = 1; i <= n; i++) {
            f = tablero.buscarFicha(i, turno);
            fichas_del_turno.add(f);
        }
        if (fichas_del_turno.size() == 4 && num_dado == 6){ 
            num_dado++;
        }
        if (num_dado == 5 && fichas_del_turno.size() < 4){
            sacarFicha(fichas_del_turno.size() + 1);
        }
        else {
            for(Ficha i : fichas_del_turno) {
                id_casilla = i.getCasilla().getPosicion();
                // if(turno == Color.AMARILLO) {
                //     if (i.getCasilla().getTipo() == TipoCasilla.PASILLO){
                //         if (id_casilla + num_dado > 75){
                //             bloqueadas.add(i);   
                //         }
                //     }
                // }
                // else if(turno == Color.AZUL) {
                //     if (i.getCasilla().getTipo() == TipoCasilla.PASILLO){
                //         if (id_casilla + num_dado > 75){
                //             bloqueadas.add(i);   
                //         }
                //     }
                // }
                // else if(turno == Color.ROJO) {
                //     if (i.getCasilla().getTipo() == TipoCasilla.PASILLO){
                //         if (id_casilla + num_dado > 75){
                //             bloqueadas.add(i);   
                //         }
                //     }
                // }
                // else if(turno == Color.VERDE) {
                //     if (i.getCasilla().getTipo() == TipoCasilla.PASILLO){
                //         if (id_casilla + num_dado > 75){
                //             bloqueadas.add(i);   
                //         }
                //     }
                // }
                if (i.getCasilla().getTipo() == TipoCasilla.PASILLO && i.getCasilla().getColor() == turno){ //¿se podría quitar condicion de turno? (si ya está en pasillo, ya se sabe que es del color del turno)
                    if (id_casilla + num_dado > 75){
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
                                if ((i.getCasilla().getTipo() == TipoCasilla.SEGURO &&
                                configBarreras == ConfigBarreras.SOLO_SEGUROS) ||
                                configBarreras == ConfigBarreras.TODAS_CASILLAS){
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

    public void realizarMovimiento(int id_ficha, int dado) {
        Ficha f = tablero.buscarFicha(id_ficha, turno);
        Casilla c;
        int id_casilla_prepasillo = 0;
        if (turno == Color.AMARILLO) {
            id_casilla_prepasillo = 67;
        }
        else if (turno == Color.AZUL){
            id_casilla_prepasillo = 16;
        }
        else if (turno == Color.ROJO){
            id_casilla_prepasillo = 33;
        }
        else if (turno == Color.VERDE){
            id_casilla_prepasillo = 50;
        }
        int id_casilla = f.getCasilla().getPosicion();
        if (id_casilla > 67){
            id_casilla = id_casilla + dado;
            c = tablero.obtenerCasillaPasillo(id_casilla, turno);
            f.getCasilla().getFichas().remove(f);
            c.getFichas().add(f);
            //HACER SAVE?
            /*comprobar si han llegado la ficha a meta. Si sí, dos cosas
             * 1. Si han llegado las 4 fichas, indicar fin de partida
             * 2. Sino, enviar al frontend para elegir ficha con la q mover
             *      10 posiciones.
            */
            if(id_casilla == 75) {
                if(c.getFichas().size() == 4) {
                    this.estado = EstadoPartida.FINALIZADA;
                }
                else {
                    comprobarMovimientos(10);
                }   
            }
        }
        else if (id_casilla + dado >= id_casilla_prepasillo){
            //entrada a pasillo
            id_casilla = 67 + ((id_casilla + dado - id_casilla_prepasillo));
            f.getCasilla().getFichas().remove(f);
            c = tablero.obtenerCasillaPasillo(id_casilla, turno);
            c.getFichas().add(f);
            f.setCasilla(c);
        }
        else {
            c = tablero.obtenerCasillaPerimetro(id_casilla + dado);
            f.getCasilla().getFichas().remove(f);
            c.getFichas().add(f);
            //COMIDAS
            if(c.getFichas().size() == 1 &&
                c.getColorPrimeraFicha() != turno && 
                c.getTipo() == TipoCasilla.COMUN) {
                c.eliminarPrimeraFicha();
                /* AVANZAR 20 CASILLAS (AVISAR FRONTEND) 
                 * Para ello, hacer otra vez el comprobarMovimientos(20)
                */
                comprobarMovimientos(20);
            }
        }  
    }
}

