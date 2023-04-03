package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
public class ResponseUsuario {
    
    private Long id;
    private String email;
    private String username;
    private int numMonedas;

    public ResponseUsuario(Long id, String email, String username, int numMonedas) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.numMonedas = numMonedas;
    }
    
}
