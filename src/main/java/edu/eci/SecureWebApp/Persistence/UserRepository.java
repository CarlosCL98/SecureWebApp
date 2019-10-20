package edu.eci.SecureWebApp.Persistence;

import edu.eci.SecureWebApp.Model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private Map<Integer, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    public int getMaxId() {
        return users.size();
    }

    public void saveUser(User user) {
        int userId = getMaxId() + 1;
        user.setId(userId);
        users.put(userId, user);
    }

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
