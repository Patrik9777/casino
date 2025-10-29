package com.casino.User.service;

import com.casino.User.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * User Service - Business Logic Layer
 * @Service annotation Spring Boot-ban
 */
public class UserService {
    private Map<String, User> userDatabase;
    private static UserService instance;
    private Long nextId = 1L;

    private UserService() {
        this.userDatabase = new HashMap<>();
        initializeTestData();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private void initializeTestData() {
        User testUser = new User("user1", "jelszo123", "user1@example.com");
        testUser.setId(nextId++);
        testUser.setBalance(1000);
        userDatabase.put("user1", testUser);
    }

    /**
     * Register new user
     */
    public boolean registerUser(String username, String password, String email) {
        if (userDatabase.containsKey(username)) {
            return false;
        }
        
        User newUser = new User(username, password, email);
        newUser.setId(nextId++);
        userDatabase.put(username, newUser);
        return true;
    }

    /**
     * Authenticate user
     */
    public Optional<User> authenticateUser(String username, String password) {
        User user = userDatabase.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    /**
     * Find user by username
     */
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userDatabase.get(username));
    }

    /**
     * Check if username exists
     */
    public boolean existsByUsername(String username) {
        return userDatabase.containsKey(username);
    }

    /**
     * Update user
     */
    public boolean updateUser(User user) {
        if (userDatabase.containsKey(user.getUsername())) {
            userDatabase.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    /**
     * Add balance to user
     */
    public boolean addBalance(String username, int amount) {
        User user = userDatabase.get(username);
        if (user != null) {
            user.addBalance(amount);
            return true;
        }
        return false;
    }

    /**
     * Deduct balance from user
     */
    public boolean deductBalance(String username, int amount) {
        User user = userDatabase.get(username);
        if (user != null) {
            return user.deductBalance(amount);
        }
        return false;
    }
}
