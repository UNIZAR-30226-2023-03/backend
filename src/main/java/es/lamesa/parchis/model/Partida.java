package es.lamesa.parchis.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.lamesa.parchis.model.dto.ResponseDado;
import es.lamesa.parchis.model.dto.ResponseMovimiento;

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

    public void empezar() {
        this.tablero = new Tablero(jugadores.size(), this);
        this.estado = EstadoPartida.EN_PROGRESO;
        int n = (int) (Math.random() * 4); //se elije de manera aleatoria el jugador que empieza en el intervalo [0,3]
        turno = Color.values()[n];
    }

    public void cambiarTurno() {
        turno.siguienteTurno();
    }

    public Ficha sacarFicha() {
        int id_casilla = tablero.obtenerSalida(turno);
        Casilla casa = tablero.obtenerCasillaCasa(turno);
        Ficha f = casa.getFichas().get(0);
        casa.getFichas().remove(f);
        Casilla c = tablero.getCasillas().get(id_casilla);
        c.getFichas().add(f);
        f.setCasilla(c);
        turno = turno.siguienteTurno();
        return f;
    }

    public ResponseDado comprobarMovimientos(int num_dado) {
        int id_casilla;
        List<Ficha> bloqueadas = tablero.obtenerFichasCasa(turno);
        List<Ficha> fichas_del_turno = tablero.obtenerFichasTablero(turno);
        Ficha comida = null;
        if (num_dado == 5 && fichas_del_turno.size() < 4 && tablero.obtenerFichasColor(tablero.obtenerSalida(turno), turno) != 2) {
            int salida = tablero.obtenerSalida(turno);
            Casilla c = tablero.getCasillas().get(salida);
            if (c.getFichas().size() == 2) {
                for (Ficha ficha : c.getFichas()) {
                    if (ficha.getColor() != turno) {
                        comida = ficha;
                        c.getFichas().remove(comida);
                        Casilla casa = tablero.obtenerCasillaCasa(comida.getColor());
                        casa.getFichas().add(comida);
                        comida.setCasilla(casa);
                        break;
                    }
                }
            }
            //Para mandar al frontend:
            Ficha ficha_sacada = sacarFicha();
            List<Ficha> fichas = new ArrayList<>();
            fichas.add(ficha_sacada);
            ResponseDado rd = new ResponseDado(fichas, true, comida, c, turno);
            return rd;
        }
        else {
            if (fichas_del_turno.size() == 4 && num_dado == 6){ 
                num_dado++;
            }
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
                                configBarreras == ConfigBarreras.TODAS_CASILLAS) {
                                    bloqueadas.add(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            ResponseDado rd = new ResponseDado(bloqueadas, false, null, null, turno);
            return rd;
        }
    }

    public ResponseMovimiento realizarMovimiento(int num_ficha, int dado) {
        Ficha f = tablero.buscarFicha(num_ficha, turno);
        Ficha comida = null;
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
            if(id_casilla == 75) {
                if(c.getFichas().size() == 4) {
                    this.estado = EstadoPartida.FINALIZADA;
                }  
            }
            else {
                turno = turno.siguienteTurno();
            }
        }
        else if (id_casilla + dado >= id_casilla_prepasillo){
            //entrada a pasillo
            id_casilla = 67 + ((id_casilla + dado - id_casilla_prepasillo));
            c = tablero.obtenerCasillaPasillo(id_casilla, turno);
            turno = turno.siguienteTurno();
        }
        else {
            c = tablero.obtenerCasillaPerimetro(id_casilla + dado);
            //COMIDAS
            if (c.getFichas().size() == 1 &&
                c.getColorPrimeraFicha() != turno && 
                c.getTipo() == TipoCasilla.COMUN) {
                    comida = c.getFichas().get(0);
                    c.eliminarPrimeraFicha();   // ¡ESTO IGUAL HAY QUE CAMBIARLO!
                    Casilla casa = tablero.obtenerCasillaCasa(comida.getColor());
                    casa.getFichas().add(comida);
                    comida.setCasilla(casa);
            }
            else {
                turno = turno.siguienteTurno();
            }
        } 
        f.getCasilla().getFichas().remove(f);
        c.getFichas().add(f);
        f.setCasilla(c);
        ResponseMovimiento rm = new ResponseMovimiento(c, comida, turno);
        return rm;
    }
}

