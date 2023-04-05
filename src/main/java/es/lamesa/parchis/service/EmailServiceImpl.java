package es.lamesa.parchis.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoElectronico(String destinatario) {
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setFrom("lamesaparchis@gmail.com");
        correo.setTo(destinatario);
        correo.setSubject("Bienvenido a la aplicación de Parchís");
        correo.setText("Hola, gracias por registrarte en nuestra aplicación de Parchís.");
        mailSender.send(correo);
    }

}

