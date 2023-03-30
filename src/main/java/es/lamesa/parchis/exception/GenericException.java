package es.lamesa.parchis.exception;

import lombok.Data;

@Data
public class GenericException extends RuntimeException {
    

    public GenericException(String mensaje) {
        super(mensaje);
    }
}
