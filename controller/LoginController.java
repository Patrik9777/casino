package controller;

import model.User;
import model.UserManager;
import view.LoginView;
import view.MainMenuView;

public class LoginController {
    private LoginView view;
    private UserManager userManager;

    public LoginController(LoginView view) {
        this.view = view;
        this.userManager = UserManager.getInstance();
    }

    public void handleLogin(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            view.showError("Kérlek töltsd ki az összes mezőt!");
            return;
        }

        User user = userManager.loginUser(username, password);
        if (user != null) {
            view.dispose();
            new MainMenuView(user);
        } else {
            view.showError("Hibás felhasználónév vagy jelszó!");
        }
    }

    public void handleRegister(String username, String password, String confirmPassword, String email) {
        if (username.trim().isEmpty() || password.trim().isEmpty() || 
            confirmPassword.trim().isEmpty() || email.trim().isEmpty()) {
            view.showError("Kérlek töltsd ki az összes mezőt!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showError("A jelszavak nem egyeznek!");
            return;
        }

        if (userManager.userExists(username)) {
            view.showError("Ez a felhasználónév már foglalt!");
            return;
        }

        if (userManager.registerUser(username, password, email)) {
            view.showError("Sikeres regisztráció! Most már bejelentkezhetsz.");
        } else {
            view.showError("Hiba történt a regisztráció során!");
        }
    }
}
