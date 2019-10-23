package edu.eci.SecureWebApp.Model;

/**
 * Esta clase representa a un usuario.
 *
 * @author Carlos Medina
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    /**
     * Constructor vacío para User.
     */
    public User() {
    }

    /**
     * Constructor con parámetros para User.
     *
     * @param id       identificador del User.
     * @param name     nombre del User.
     * @param email    correo único del User.
     * @param password contraseña del User.
     */
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
