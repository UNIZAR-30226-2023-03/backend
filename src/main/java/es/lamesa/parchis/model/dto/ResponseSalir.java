package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data

public class ResponseSalir {
    
    @Schema(description = "True si se ha salido con éxito de la partida")
    private boolean exito;
    @Schema(description = "True si el jugador es el último finalista de un torneo")
    private boolean finalTorneo;
    
    public ResponseSalir(boolean exito, boolean finalTorneo) {
        this.exito = exito;
        this.finalTorneo = finalTorneo;
    }

}
