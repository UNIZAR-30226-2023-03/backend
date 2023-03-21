package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private String email;
    private String username;
    private String password;
}
