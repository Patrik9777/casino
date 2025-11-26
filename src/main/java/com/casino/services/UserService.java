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

    /**
     * create new User
     */
    public User createNew(String username, String email, String password) {
        User testUser = new User();
        testUser.username = username;
        testUser.email =email;
        testUser.password = password;
        testUser.balance = 0;
        repository.save(testUser);
        return testUser;
    }

    /**
     * Find User by ID
     */
    public Optional<User> findUser(Integer id) {
        return repository.findById(id);
    }

    /**
     * Find User by UserName and password
     */
    public Optional<User> findUser(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }

    /**
     * Save user
     */
    public void saveUser(User user) {
        repository.save(user);
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
     * Remove user
     */
    public void removeUser(User user) {
        repository.delete(user);
    }
}
