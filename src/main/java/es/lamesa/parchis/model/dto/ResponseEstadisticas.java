package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ResponseEstadisticas {

    @Schema(description = "Username del usuario")
    private String username;
    @Schema(description = "Partidas jugadas por el usuario")
    private int pJugadas;
    @Schema(description = "Partidas ganadas por el usuario")
    private int pGanadas;
    @Schema(description = "Media de fichas comidas por el usuario")
    private float mediaComidas;
    @Schema(description = "Media de fichas metidas en meta por el usuario")
    private float mediaEnMeta;
    @Schema(description = "Torneos jugados por el usuario")
    private int tJugados;
    @Schema(description = "Torneos ganados por el usuario")
    private int tGanados;

    public ResponseEstadisticas(String username, int pJugadas, int pGanadas, float mediaComidas, float mediaEnMeta, int tJugados, int tGanados) {
        this.username = username;
        this.pJugadas = pJugadas;
        this.pGanadas = pGanadas;
        this.mediaComidas = mediaComidas;
        this.mediaEnMeta = mediaEnMeta;
        this.tJugados = tJugados;
        this.tGanados = tGanados;
    }

}
