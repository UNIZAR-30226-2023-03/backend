package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import es.lamesa.parchis.model.ConfigBarreras;
import es.lamesa.parchis.model.ConfigFichas;

@Data
public class RequestPartidaPublica {

    @Schema(description = "Id del jugador")
    private Long jugador;
    @Schema(description = "Configuración de barreras elegida")
    private ConfigBarreras configuracionB;
    @Schema(description = "Configuración de fichas elegida (partida rápida o normal)")
    private ConfigFichas configuracionF;
}

