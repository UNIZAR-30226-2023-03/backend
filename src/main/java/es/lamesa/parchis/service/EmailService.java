package es.lamesa.parchis.service;

import org.springframework.scheduling.annotation.Async;

import es.lamesa.parchis.model.TipoEmail;

public interface EmailService {

    @Async
    public void enviarCorreoElectronico(String destinatario, String username, TipoEmail tipo);

    @Async
    public void enviarCorreoRecuperacion(String destinatario, String username, String token);
        
}
