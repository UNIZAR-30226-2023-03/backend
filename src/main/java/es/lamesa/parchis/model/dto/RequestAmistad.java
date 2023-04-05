package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestAmistad {

    @Schema(description = "Id del usuario que envía la solicitud")
    private Long usuario;
    @Schema(description = "Id del usuario al que se envía la solicitud")
    private Long amigo;
    
}
