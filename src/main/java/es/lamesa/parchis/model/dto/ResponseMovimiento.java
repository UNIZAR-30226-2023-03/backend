package es.lamesa.parchis.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import es.lamesa.parchis.model.Ficha;
import es.lamesa.parchis.model.Casilla;
import es.lamesa.parchis.model.Color;

@Data
public class ResponseMovimiento {

    @Schema(description = "Casilla destino para la ficha tras el movimiento")
    private Casilla destino;
    @Schema(description = "Casilla comida, en caso de haberla")
    private Ficha comida;
    @Schema(description = "Siguiente turno")
    private Color turno;
    @Schema(description = "True si con este movimiento la partida ha finalizado")
    private boolean acabada;
    @Schema(description = "Ficha movida en el movimiendo")
    private Ficha ficha;
    @Schema(description = "Booleano que indica si el jugador es el último en " +
                          "haber ganado su partida en un torneo")
    boolean finalTorneo;

    public ResponseMovimiento(Casilla destino, Ficha comida, Color turno, boolean acabada, Ficha ficha) {
        this.destino = destino;
        this.comida = comida;
        this.turno = turno;
        this.acabada = acabada;
        this.ficha = ficha;
        this.finalTorneo = false;
    }

}
