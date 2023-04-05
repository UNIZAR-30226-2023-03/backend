package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestLogin {

    @Schema(description = "Username o correo del usuario")
    private String login;
    @Schema(description = "Contraseña del usuario")
    private String password;
    
}
