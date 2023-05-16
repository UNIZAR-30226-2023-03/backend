package es.lamesa.parchis.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import es.lamesa.parchis.model.TipoEmail;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoElectronico(String destinatario, String username, TipoEmail tipo) {
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setFrom("lamesaparchis@gmail.com");
        correo.setTo(destinatario);
        if (tipo == TipoEmail.REGISTRO) {
            correo.setSubject("Bienvenido a la aplicación de Parchís");
            correo.setText("Hola " + username + ", gracias por registrarte en nuestra aplicación de Parchís.\n" + 
            "Disfrute del juego");
        }
        else if (tipo == TipoEmail.CAMBIO_EMAIL) {
            correo.setSubject("Cambio de correo electrónico");
            correo.setText("Hola " + username + ", le informamos de que su correo electrónico ha sido modificada de manera correcta.\n" +
            "Si no has realizado esta acción, por favor ponte en " + "contacto con nosotros lo antes posible.");
        }
        else if (tipo == TipoEmail.CAMBIO_PASSWORD) {
            correo.setSubject("Cambio de contraseña");
            correo.setText("Hola " + username + ", le informamos de que su contraseña ha sido modificada de manera correcta.\n" +
            "Si no has realizado esta acción, por favor ponte en " + "contacto con nosotros lo antes posible.");
        }
        else if(tipo == TipoEmail.CAMBIO_USERNAME) {
            correo.setSubject("Cambio de nombre de usuario");
            correo.setText("Hola " + username + ", le informamos de que se ha cambiado correctamente su nombre de usuario en la aplicación.\n" +
            "Si no has realizado esta acción, por favor ponte en " + "contacto con nosotros lo antes posible.");
        }
        mailSender.send(correo);
    }

    public void enviarCorreoRecuperacion(String destinatario, String username, String token) {
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setFrom("lamesaparchis@gmail.com");
        correo.setTo(destinatario);
        correo.setSubject("Recuperación de contraseña");
        correo.setText("Hola " + username + ", le informamos de que se ha realizado una solicitud de recuperación de contraseña. " + 
        "Para continuar, acceda al siguiente enlace:\nhttp://localhost:3000/recuperar-contrasena?token=" + token + "\n" +
        "Si no has realizado esta acción, por favor ponte en " + "contacto con nosotros lo antes posible.");
        mailSender.send(correo);
    }

}
