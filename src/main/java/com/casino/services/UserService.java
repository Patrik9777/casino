package com.casino.services;

import com.casino.models.User;
import com.casino.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User Service - Business Logic Layer
 * @Service annotation Spring Boot-ban
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    private static Integer nextId = 1;

    public void initializeTestData() {
        User testUser = new User();
        testUser.id = nextId++;
        testUser.username = "test1";
        testUser.email = "user1@example.com";
        testUser.password = "jelszo123";
        testUser.balance = 1000;
        repository.save(testUser);
    }

    /**
     * Register new user
     */
    public Optional<User> registerUser(String username, String password, String email) {

        var foundUser = repository.findByUsernameAndPassword(username, password);
        if (foundUser.isPresent())
            return Optional.empty();
        User newUser = new User();
        newUser.id = nextId++;
        newUser.username = username;
        newUser.email = email;
        newUser.password = password;
        newUser.balance = 1000;
        repository.save(newUser);
        return Optional.of(newUser);
    }

    /**
     * Authenticate user
     */
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent() && user.get().password.equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    /**
     * Find user by username
     */
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    /**
     * Check if username exists
     */
    public boolean existsByUsername(String username) {
        return false;
    }

    /**
     * Update user
     */
    public boolean updateUser(User user) {
        if (repository.existsById(user.id)) {
            repository.save(user);
            return true;
        }
        return false;
    }

    /**
     * Add balance to user
     */
    public boolean addBalance(Integer id, int amount) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            User realUser = user.get();
            realUser.balance += amount;
            repository.save(realUser);
            return true;
        }
        return false;
    }

    /**
     * Deduct balance from user
     */
    public boolean deductBalance(Integer id, int amount) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            User realUser = user.get();
            realUser.balance -= amount;
            repository.save(realUser);
            return true;
        }
        return false;
    }
}
