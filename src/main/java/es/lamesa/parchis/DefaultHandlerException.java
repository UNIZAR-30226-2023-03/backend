package es.lamesa.parchis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import es.lamesa.parchis.exception.GenericException;

@RestControllerAdvice
public class DefaultHandlerException extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<String> GenericException(GenericException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
}
