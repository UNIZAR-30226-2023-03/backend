package es.lamesa.parchis.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import es.lamesa.parchis.model.ConfigFichas;
import es.lamesa.parchis.model.ConfigBarreras;

@Data
public class RequestTorneoCrear {
    @Schema(description = "Id del usuario que crea el torneo")
    private Long usuario;
    @Schema(description = "Precio de entrada del torneo")
    private int precio;
    @Schema(description = "Nombre del torneo")
    private ConfigBarreras configBarreras;
    @Schema(description = "Configuración de las fichas del torneo")
    private ConfigFichas configFichas;
    @Schema(description = "Nombre del torneo")
    private String nombre;
}
