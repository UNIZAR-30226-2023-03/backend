package es.lamesa.parchis.model.dto;

import lombok.Data;
import es.lamesa.parchis.model.EstadoPartida;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ResponseAmistad {

    @Schema(description = "Id del usuario")
	private Long id;
    @Schema(description = "Username del usuario")
    private String username;
    @Schema(description = "Indica si el usuario está jugando una partida")
    private EstadoPartida estado;
    @Schema(description = "Id de la partida que está jugando uno de tus amigos")
    private Long idPartida;

    public ResponseAmistad(Long id, String username, EstadoPartida estado, Long idP) {
        this.id = id;
        this.username = username;
        this.estado = estado;
        this.idPartida = idP;
    }

}
