package es.lamesa.parchis.exception;

import lombok.Data;

@Data
public class NoExisteUsuarioException extends RuntimeException {
    
    private String mensaje = "No existe un usuario con ese login.";

    public NoExisteUsuarioException() {
        super();
        // this.mensaje = mensaje;
    }
}
