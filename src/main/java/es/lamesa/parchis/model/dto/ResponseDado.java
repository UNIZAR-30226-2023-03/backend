package es.lamesa.parchis.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import es.lamesa.parchis.model.Ficha;
import es.lamesa.parchis.model.Casilla;
import es.lamesa.parchis.model.Color;

@Data
public class ResponseDado {
    
    @Schema(description = "Si sacar es false, representa las fichas que no se pueden mover con la tirada realizada. " +
                          "Si es true, representa la ficha a mover a la casilla de salida tras sacar un 5")
    private List<Ficha> fichas;
    @Schema(description = "True si tras sacar un 5 se puede mover una ficha de casa a la casilla de salida")
    private boolean sacar;
    @Schema(description = "Ficha comida, en caso de que la haya si se saca una ficha de cada")
    private Ficha comida;
    @Schema(description = "Casilla a la que se mueve la casilla que se saca de casa, en caso de que así sea")
    private Casilla casilla;
    @Schema(description = "Siguiente turno")
    private Color turno;
    @Schema(description = "True si tras sacar tres 6 seguidos se mete una ficha en casa")
    private boolean vueltaACasa;
    // @Schema(description = "True si has sacado un seis y tienes una barrera y por lo tanto tienes que abrir")
    // private boolean sacarBarrera;

    public ResponseDado(List<Ficha> fichas, boolean sacar, Ficha comida, Casilla casilla, Color turno, boolean vueltaACasa) {
        this.fichas = fichas;
        this.sacar = sacar;
        this.comida = comida;
        this.casilla = casilla;
        this.turno = turno;
        this.vueltaACasa = vueltaACasa;
    }

}
