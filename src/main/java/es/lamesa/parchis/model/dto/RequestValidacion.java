package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestValidacion {

    @Schema(description = "Id del usuario que cambia la contraseña")
    private Long usuario;
    @Schema(description = "Token de validación")
    private String token;
    @Schema(description = "Contraseña del usuario a modificar")
    private String password;
    
}
