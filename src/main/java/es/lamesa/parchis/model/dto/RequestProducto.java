package es.lamesa.parchis.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestProducto {

    @Schema(description = "Id del usuario que realiza la compra")
    private Long usuario;
    @Schema(description = "Id del producto que se quiere adquirir")
    private Long producto;
    
}
