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

    public ResponseMovimiento(Casilla destino, Ficha comida, Color turno, boolean acabada) {
        this.destino = destino;
        this.comida = comida;
        this.turno = turno;
        this.acabada = acabada;
    }

}
