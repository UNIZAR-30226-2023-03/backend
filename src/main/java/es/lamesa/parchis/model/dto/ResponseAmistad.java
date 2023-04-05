package es.lamesa.parchis.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ResponseAmistad {

    @Schema(description = "Id del usuario")
	private Long id;
    @Schema(description = "Username del usuario")
    private String username;

    public ResponseAmistad(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}
