package es.lamesa.parchis.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestTorneo {

    @Schema(description = "Id del usuario que se apunta al torneo")
    private Long usuario;
    @Schema(description = "Id del torneo al que se apunta el jugador")
    private Long torneo;
    
}
