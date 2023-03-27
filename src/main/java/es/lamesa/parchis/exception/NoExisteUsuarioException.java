package es.lamesa.parchis.exception;

import lombok.Data;

@Data
public class NoExisteUsuarioException extends RuntimeException {
    
    private String mensaje_error;

    public NoExisteUsuarioException(String mensaje) {
        super();
        this.mensaje_error = mensaje;
    }
}
