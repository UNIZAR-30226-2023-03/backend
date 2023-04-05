package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestCambio {

    @Schema(description = "Id del usuario")
    private Long id;
    @Schema(description = "Nuevo valor del campo a cambiar")
    private String cambio;

}
