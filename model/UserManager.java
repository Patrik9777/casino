package model;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;
    private static UserManager instance;

    private UserManager() {
        users = new HashMap<>();
        // Teszt felhasználó
        User testUser = new User("user1", "jelszo123", "user1@example.com");
        testUser.setBalance(1000);
        users.put("user1", testUser);
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public boolean registerUser(String username, String password, String email) {
        if (users.containsKey(username)) {
            return false;
        }
        User newUser = new User(username, password, email);
        users.put(username, newUser);
        return true;
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public User getUser(String username) {
        return users.get(username);
    }
}
