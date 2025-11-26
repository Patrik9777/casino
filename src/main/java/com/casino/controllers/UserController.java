package com.casino.controllers;

import com.casino.models.User;
import com.casino.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Authenticate user
     */
    public Optional<User> authenticateUser(String username, String password) {
        return userService.findUser(username, password);
    }


    /// Registering user, return empty if not succeed.
    public Optional<User> registerUser(String username, String password, String email) {
        var foundUser = userService.findUser(username, password);
        if (foundUser.isPresent())
            return Optional.empty();
        return Optional.of(userService.createNew(username, email, password));
    }


    /**
     * Add balance to user
     */
    public boolean addBalance(Integer id, int amount) {
        Optional<User> user = userService.findUser(id);
        if (user.isPresent()) {
            User realUser = user.get();
            realUser.balance += amount;
            userService.saveUser(realUser);
            return true;
        }
        return false;
    }

    /**
     * Deduct balance from user
     */
    public boolean deductBalance(Integer id, int amount) {
        Optional<User> user = userService.findUser(id);
        if (user.isPresent()) {
            User realUser = user.get();
            realUser.balance -= amount;
            userService.saveUser(realUser);
            return true;
        }
        return false;
    }

    public void setNewBalance(User user, int amount) {
        user.balance = amount;
        userService.saveUser(user);
    }

}
