package es.lamesa.parchis.model.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestPartidaAmigo {
    @Schema(description = "Id del jugador que se une a la partida")
    private Long jugador;
    @Schema(description = "Id de la partida a unirse")
    private Long partida;
}
