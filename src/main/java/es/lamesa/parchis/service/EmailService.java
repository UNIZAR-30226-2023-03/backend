package es.lamesa.parchis.service;

import es.lamesa.parchis.model.TipoEmail;

public interface EmailService {

    public void enviarCorreoElectronico(String destinatario, String username, TipoEmail tipo);

    public void enviarCorreoRecuperacion(String destinatario, String username, String token);
        
}
