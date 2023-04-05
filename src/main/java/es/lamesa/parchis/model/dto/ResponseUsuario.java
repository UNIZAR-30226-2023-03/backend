package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ResponseUsuario {
    
    @Schema(description = "Id asignado al usuario")
    private Long id;
    @Schema(description = "Correo del usuario")
    private String email;
    @Schema(description = "Username del usuario")
    private String username;
    @Schema(description = "Número de monedas del usuario")
    private int numMonedas;

    public ResponseUsuario(Long id, String email, String username, int numMonedas) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.numMonedas = numMonedas;
    }
    
}
