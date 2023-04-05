package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class MensajeDto {

    @Schema(description = "Username del usuario que envía el mensaje")
    private String usuario;
    @Schema(description = "Mensaje enviado")
    private String mensaje;

}
