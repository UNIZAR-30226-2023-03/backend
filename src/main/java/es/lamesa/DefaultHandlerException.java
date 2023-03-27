package es.lamesa;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import es.lamesa.parchis.exception.NoExisteUsuarioException;

@RestControllerAdvice
public class DefaultHandlerException extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler
    public ResponseEntity<String> noExisteUsuarioException(NoExisteUsuarioException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
