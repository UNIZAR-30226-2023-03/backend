package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import es.lamesa.parchis.model.Color;

@Data
public class UsuarioColorDto {
    
    @Schema(description = "Username del usuario")
    String username;
    @Schema(description = "Color asignado al usuario")
    Color color;

    public UsuarioColorDto(String username, Color color) {
        this.username = username;
        this.color = color;
    }

}
