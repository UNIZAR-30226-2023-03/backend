package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
public class AmigosDto {

	private Long id;
    private String username;

    public AmigosDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}
