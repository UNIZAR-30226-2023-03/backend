package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data 
public class RequestConexion {
    private String nombre;
    private String password;
    private Long jugador;
}
