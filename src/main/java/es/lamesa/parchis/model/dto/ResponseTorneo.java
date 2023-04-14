package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ResponseTorneo {
    
    @Schema(description = "True si el usuario se ha apuntado al torneo correctamente. Suscribirse a websocket")
    private boolean apuntado;
    @Schema(description = "True si es el jugador nº16, de forma que el torneo puede comenzar")
    private boolean esJugador16;

    public ResponseTorneo(boolean apuntado, boolean esJugador16) {
        this.apuntado = apuntado;
        this.esJugador16 = esJugador16;
    }

}
