package com.casino.controller;

import com.casino.model.User;
import com.casino.service.UserService;
import com.casino.view.LoginView;
import com.casino.view.MainMenuView;

import java.util.Optional;

/**
 * Authentication Controller
 * Spring Boot-ban @RestController vagy @Controller
 */
public class AuthController {
    private UserService userService;
    private LoginView loginView;

    public AuthController(LoginView loginView) {
        this.loginView = loginView;
        this.userService = UserService.getInstance();
    }

    /**
     * Handle login request
     */
    public void handleLogin(String username, String password) {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            loginView.showError("A felhasználónév nem lehet üres!");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            loginView.showError("A jelszó nem lehet üres!");
            return;
        }

        // Authentication
        Optional<User> userOpt = userService.authenticateUser(username, password);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            loginView.dispose();
            new MainMenuView(user);
        } else {
            loginView.showError("Hibás felhasználónév vagy jelszó!");
        }
    }

    /**
     * Handle registration request
     */
    public void handleRegister(String username, String password, String confirmPassword, String email) {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            loginView.showError("A felhasználónév nem lehet üres!");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            loginView.showError("A jelszó nem lehet üres!");
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            loginView.showError("Az email nem lehet üres!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            loginView.showError("A jelszavak nem egyeznek!");
            return;
        }

        // Check if user exists
        if (userService.existsByUsername(username)) {
            loginView.showError("Ez a felhasználónév már foglalt!");
            return;
        }

        // Register user
        boolean success = userService.registerUser(username, password, email);
        
        if (success) {
            loginView.showError("Sikeres regisztráció! Most már bejelentkezhetsz.");
        } else {
            loginView.showError("Hiba történt a regisztráció során!");
        }
    }
}
