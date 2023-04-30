package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ResponseTorneo {
    
    @Schema(description = "Id del torneo que se ha creado o al que se ha unido un usuario")
    private Long id;
    @Schema(description = "True si el usuario se ha apuntado al torneo correctamente. Suscribirse a websocket")
    private boolean apuntado;
    @Schema(description = "True si es el jugador nº16, de forma que el torneo puede comenzar")
    private boolean esJugador16;

    public ResponseTorneo(Long id, boolean apuntado, boolean esJugador16) {
        this.id = id;
        this.apuntado = apuntado;
        this.esJugador16 = esJugador16;
    }

}
