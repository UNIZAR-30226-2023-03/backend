package es.lamesa.parchis.service;

import es.lamesa.parchis.model.TipoEmail;

public interface EmailService {

    public void enviarCorreoElectronico(String email, TipoEmail tipo);
    
}
