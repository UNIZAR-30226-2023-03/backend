package es.lamesa.parchis.model.dto;

import lombok.Data;
import es.lamesa.parchis.model.ConfigBarreras;
import es.lamesa.parchis.model.ConfigFichas;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class TorneoDto {

    @Schema(description = "Id del torneo")
    private Long id;
    @Schema(description = "Nombre del torneo")
    private String nombre;
    @Schema(description = "Precio de la entrada del torneo")
    private int precioEntrada;
    @Schema(description = "Configuración de las barreras")
    private ConfigBarreras cb;
    @Schema(description = "Configuración de las fichas")
    private ConfigFichas cf;


    public TorneoDto(Long id, String nombre, int precioEntrada, ConfigBarreras cb, ConfigFichas cf) {
        this.id = id;
        this.nombre = nombre;
        this.precioEntrada = precioEntrada;
        this.cb = cb;
        this.cf = cf;
    }

}
