package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestMovimiento {

    @Schema(description = "Id de la partida donde se realiza el movimiento")
    private Long partida;
    @Schema(description = "Número de ficha a mover (1, 2, 3 o 4 del color correspondiente)")
    private int ficha;
    @Schema(description = "Número de casillas a mover")
    private int dado;

}
