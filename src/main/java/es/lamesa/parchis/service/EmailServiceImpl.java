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

    public void enviarCorreoElectronico(String destinatario, TipoEmail tipo) {
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setFrom("lamesaparchis@gmail.com");
        correo.setTo(destinatario);
        if (tipo == TipoEmail.REGISTRO) {
            correo.setSubject("Bienvenido a la aplicación de Parchís");
            correo.setText("Hola, gracias por registrarte en nuestra aplicación de Parchís.");
        }
        else if (tipo == TipoEmail.RECUPERACION) {
            correo.setSubject("Recuperación de contraseña");
            correo.setText("Hola, le informamos de que su contraseña ha sido modificada de manera correcta." +
            "Si no has realizado esta acción, por favor ponte en " + "contacto con nosotros lo antes posible.");
        }
        else if (tipo == TipoEmail.CAMBIO_EMAIL) {
            correo.setSubject("Cambio de correo electrónico");
            correo.setText("Hola, le informamos de que su correo electrónico ha sido modificada de manera correcta." +
            "Si no has realizado esta acción, por favor ponte en " + "contacto con nosotros lo antes posible.");
        }
        else if (tipo == TipoEmail.CAMBIO_PASSWORD) {
            correo.setSubject("Cambio de contraseña");
            correo.setText("Hola, le informamos de que su contraseña ha sido modificada de manera correcta." +
            "Si no has realizado esta acción, por favor ponte en " + "contacto con nosotros lo antes posible.");
        }
        else if(tipo == TipoEmail.CAMBIO_USERNAME) {
            correo.setSubject("Cambio de nombre de usuario");
            correo.setText("Hola, le informamos de que se ha cambiado correctamente su nombre de usuario en la aplicación." +
            "Si no has realizado esta acción, por favor ponte en " + "contacto con nosotros lo antes posible.");
        }
        mailSender.send(correo);
    }

}

