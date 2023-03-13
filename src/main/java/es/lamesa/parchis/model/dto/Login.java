package es.lamesa.parchis.model.dto;

/**
 * Clase que representa las credenciales de un usuario para iniciar sesión.
 */
public class Login {
    /**
     * Nombre de usuario o correo electrónico.
     */
    private String login;
    /**
     * Contraseña del usuario.
     */
    private String password;
    /**
     * Devuelve el nombre de usuario o correo electrónico.
     * 
     * @return El nombre de usuario o correo electrónico.
     */
    public String getLogin() {
        return this.login;
    }
    /**
    * Establece el nombre de usuario o correo electrónico.
    * 
    * @param login El nombre de usuario o correo electrónico.
    */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * Devuelve la contraseña del usuario.
     * 
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * Establece la contraseña del usuario.
     * 
     * @param password La contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
