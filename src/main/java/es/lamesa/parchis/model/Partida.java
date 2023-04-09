package es.lamesa.parchis.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.lamesa.parchis.model.dto.ResponseDado;
import es.lamesa.parchis.model.dto.ResponseMovimiento;

@EqualsAndHashCode(exclude = "tablero")
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

    @OneToOne(mappedBy = "partida", cascade = CascadeType.ALL)
    private Tablero tablero; //una vez finalizada la partida, se podría borrar?

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "final_torneo_id")
    private Torneo finalTorneo;

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
    private ConfigFichas configFichas;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPartida estado;

    public void empezar() {
        this.tablero = new Tablero(jugadores.size(), this);
        this.estado = EstadoPartida.EN_PROGRESO;
        int n = (int) (Math.random() * jugadores.size()); //se elije de manera aleatoria el jugador que empieza en el intervalo [0,3]
        turno = Color.values()[n];
    }

    public void cambiarTurno() {
        turno.siguienteTurno(jugadores.size());
    }

    public Ficha sacarFicha() {
        int id_casilla = tablero.obtenerSalida(turno);
        Casilla casa = tablero.obtenerCasillaCasa(turno);
        Ficha f = casa.getFichas().get(0);
        casa.getFichas().remove(f);
        Casilla c = tablero.getCasillas().get(id_casilla);
        c.getFichas().add(f);
        f.setCasilla(c);
        return f;
    }

    public ResponseDado comprobarMovimientos(int num_dado) {
        int id_casilla;
        int num_fichas = 4;
        if (configFichas == ConfigFichas.RAPIDO) {
            num_fichas = 2;
        }
        List<Ficha> bloqueadas = tablero.obtenerFichasCasa(turno); 
        List<Ficha> meta = tablero.obtenerFichasMeta(turno);
        for (Ficha f : meta){
            bloqueadas.add(f);
        }
        List<Ficha> fichas_del_turno = tablero.obtenerFichasTablero(turno);
        Ficha comida = null;
        if (num_dado == 5 && fichas_del_turno.size() < num_fichas && tablero.obtenerFichasColor(tablero.obtenerSalida(turno), turno) != 2) {
            int salida = tablero.obtenerSalida(turno);
            Casilla c = tablero.getCasillas().get(salida);
            boolean ficha_comida = false;
            if (c.getFichas().size() == 2) {
                for (Ficha ficha : c.getFichas()) {
                    if (ficha.getColor() != turno) {
                        comida = ficha;
                        c.getFichas().remove(comida);
                        Casilla casa = tablero.obtenerCasillaCasa(comida.getColor());
                        casa.getFichas().add(comida);
                        comida.setCasilla(casa);
                        comida.setNumPasos(0);
                        ficha_comida = true;
                        break;
                    }
                }
            }
            //Para mandar al frontend:
            Ficha ficha_sacada = sacarFicha();
            List<Ficha> fichas = new ArrayList<>();
            fichas.add(ficha_sacada);
            if (!ficha_comida) {
                turno = turno.siguienteTurno(jugadores.size());
            }
            ResponseDado rd = new ResponseDado(fichas, true, comida, c, turno);
            return rd;
        }
        else {
            if (fichas_del_turno.size() == num_fichas && num_dado == 6){ 
                num_dado++;
            }
            for(Ficha i : fichas_del_turno) {
                id_casilla = i.getCasilla().getPosicion();
                if (i.getNumPasos() + num_dado > 71){ //¿se podría quitar condicion de turno? (si ya está en pasillo, ya se sabe que es del color del turno)
                    bloqueadas.add(i);
                }
                else if (num_dado == 5 && tablero.obtenerFichasColor(tablero.obtenerSalida(turno), turno) == 2) {
                    bloqueadas.add(i);
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
                                if (configBarreras == ConfigBarreras.SOLO_SEGUROS){
                                    if (tablero.obtenerCasillaPerimetro(j).getTipo() == TipoCasilla.SEGURO || 
                                        tablero.obtenerCasillaPerimetro(j).getTipo() == TipoCasilla.ENTRADA || 
                                        tablero.obtenerCasillaPerimetro(j).getTipo() == TipoCasilla.SALIDA) {
                                        bloqueadas.add(i);
                                        break;
                                    }
                                }
                                else {
                                    bloqueadas.add(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (bloqueadas.size() == num_fichas && num_dado <= 6){
                turno = turno.siguienteTurno(jugadores.size());
            }
            ResponseDado rd = new ResponseDado(bloqueadas, false, null, null, turno);
            return rd;
        }
    }

    public ResponseMovimiento realizarMovimiento(int num_ficha, int dado) {
        Ficha f = tablero.buscarFicha(num_ficha, turno);
        Ficha comida = null;
        Casilla c;
        boolean acabada = false;
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
        if (id_casilla > 67) {
            id_casilla = id_casilla + dado;
            c = tablero.obtenerCasillaPasillo(id_casilla, turno);
            if (id_casilla == 75) {
                if (c.getFichas().size() == 3 && configFichas == ConfigFichas.NORMAL || 
                    c.getFichas().size() == 1 && configFichas == ConfigFichas.RAPIDO) {
                    this.estado = EstadoPartida.FINALIZADA;
                    acabada = true;
                    jugadores.get(turno.ordinal()).setGanador(true);
                }
            }
            else {
                turno = turno.siguienteTurno(jugadores.size());
            }
        } 
        else if (f.getNumPasos() + dado > 63) {
            //entrada a pasillo
            id_casilla = 67 + ((id_casilla + dado - id_casilla_prepasillo)%68);
            c = tablero.obtenerCasillaPasillo(id_casilla, turno);
            turno = turno.siguienteTurno(jugadores.size());
        }
        else {
            c = tablero.obtenerCasillaPerimetro((id_casilla + dado)%68);
            //COMIDAS
            if (c.getFichas().size() == 1 &&
                c.obtenerColorPrimeraFicha() != turno && 
                c.getTipo() == TipoCasilla.COMUN) {
                    comida = c.getFichas().get(0);
                    c.eliminarPrimeraFicha();  
                    Casilla casa = tablero.obtenerCasillaCasa(comida.getColor());
                    casa.getFichas().add(comida);
                    comida.setCasilla(casa);
                    comida.setNumPasos(0);
            }
            else {
                turno = turno.siguienteTurno(jugadores.size());
            }
        } 
        f.getCasilla().getFichas().remove(f);
        c.getFichas().add(f);
        f.setCasilla(c);
        f.setNumPasos(f.getNumPasos() + dado);
        ResponseMovimiento rm = new ResponseMovimiento(c, comida, turno, acabada, f);
        return rm;
    }
}
