package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestUsuario {

    @Schema(description = "Correo del usuario a crear")
    private String email;
    @Schema(description = "Username del usuario a crear")
    private String username;
    @Schema(description = "Contraseña del usuario a crear")
    private String password;
    
}
