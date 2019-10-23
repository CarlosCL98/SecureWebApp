package edu.eci.SecureWebApp.Persistence;

import edu.eci.SecureWebApp.Model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase representa el repositorio de los usuarios para persistirlos.
 *
 * @author Carlos Medina
 */
public class UserRepository {

    private Map<Integer, User> users;

    /**
     * Constructor de UserRepository
     */
    public UserRepository() {
        this.users = new HashMap<>();
    }

    /**
     * Obtiene el Id máximo de los usuarios que hay hasta el momento.
     *
     * @return int representa el id máximo.
     */
    public int getMaxId() {
        return users.size();
    }

    /**
     * Almacena a un usuario que se registra.
     *
     * @param user representa al usuario registrado.
     */
    public void saveUser(User user) {
        int userId = getMaxId() + 1;
        user.setId(userId);
        users.put(userId, user);
    }

    /**
     * Obtiene a un usuario por su correo.
     *
     * @param email correo del usuario.
     * @return User representa al usuario encontrado.
     */
    public User getUserByEmail(String email) {
        User user = null;
        for (Integer userId : users.keySet()) {
            if (email.equals(users.get(userId).getEmail())) {
                user = users.get(userId);
            }
        }
        return user;
    }
}
