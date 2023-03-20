package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
/**
 * Clase que representa las credenciales de un usuario para iniciar sesión.
 */
public class LoginDto {
    /**
     * Nombre de usuario o correo electrónico.
     */
    private String login;
    /**
     * Contraseña del usuario.
     */
    private String password;
}
