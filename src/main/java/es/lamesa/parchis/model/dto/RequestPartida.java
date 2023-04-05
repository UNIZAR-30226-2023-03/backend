package es.lamesa.parchis.model.dto;

import lombok.Data;

import es.lamesa.parchis.model.ConfigBarreras;
import es.lamesa.parchis.model.ConfigFichas;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestPartida {

    @Schema(description = "Nombre de la sala/partida")
    private String nombre;
    @Schema(description = "Contraseña de la sala/partida")
    private String password;
    @Schema(description = "Id del jugador que crea la partida")
    private Long jugador;
    @Schema(description = "Configuración de barreras elegida")
    private ConfigBarreras configuracionB;
    @Schema(description = "Configuración de fichas elegida (partida rápida o normal)")
    private ConfigFichas configuracionF;

}
