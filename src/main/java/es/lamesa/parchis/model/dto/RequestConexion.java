package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data 
public class RequestConexion {

    @Schema(description = "Nombre de la sala/partida")
    private String nombre;
    @Schema(description = "Contraseña de la sala/partida")
    private String password;
    @Schema(description = "Id del jugador que se une a la partida")
    private Long jugador;
    
}
