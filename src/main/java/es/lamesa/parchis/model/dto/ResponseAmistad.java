package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
public class ResponseAmistad {

	private Long id;
    private String username;

    public ResponseAmistad(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}
