package com.casino.service;

import com.casino.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserService {
    private static UserService instance;
    private Map<String, User> users;
    
    private UserService() {
        users = new HashMap<>();
        // Add default test user with $0 balance
        User testUser = new User("user1", "jelszo123", "user1@casino.com");
        testUser.setBalance(0); // Start with $0
        users.put("user1", testUser);
    }
    
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    
    public boolean registerUser(String username, String password, String email) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password, email));
        return true;
    }
    
    public Optional<User> authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
