package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
public class RequestUsuario {

    private String email;
    private String username;
    private String password;
    
}
