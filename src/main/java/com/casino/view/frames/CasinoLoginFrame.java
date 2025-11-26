package com.casino.view.frames;

import com.casino.controllers.UserController;
import com.casino.models.User;
import com.casino.view.Constants;
import com.casino.view.dialogs.RegisterDialog;
import com.casino.view.panels.LoginPanel;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

@Controller
@Getter
public class CasinoLoginFrame extends JFrame {

    @Autowired
    private UserController userController;

    @Autowired
    private RegisterDialog registerDialog;

    @Autowired
    private LoginPanel loginPanel;

    @Autowired
    private MainMenuFrame mainMenuFrame;

    protected int xMouse = 0, yMouse = 0;

    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
        logic();
    }

    private void setUp() {
        setUndecorated(true);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
    }

    private void initComponents() {
        add(loginPanel);
    }

    private void logic() {
        // Eseménykezelők
        loginPanel.loginButton.addActionListener(e -> login());
        loginPanel.toRegisterPanel.registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showRegistrationDialog();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                loginPanel.toRegisterPanel.registerLink.setForeground(Constants.LINK_COLOR.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                loginPanel.toRegisterPanel.registerLink.setForeground(Constants.LINK_COLOR);
            }
        });
        
        // Húzás a címsor nélküli ablakhoz
        loginPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY();
            }
        });

        loginPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - xMouse, y - yMouse);
            }
        });
    }
    
    private void login() {
        String username = loginPanel.usernameField.getText().trim();
        String password = new String(loginPanel.passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Kérjük töltsd ki mindkét mezőt!");
            return;
        }
        
        java.util.Optional<User> userOpt = userController.authenticateUser(username, password);
        if (userOpt.isPresent()) {
            // Open main menu in the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                System.out.println("user found!");
                mainMenuFrame.setUser(userOpt.get());
                mainMenuFrame.setVisible(true);
                this.dispose(); // Close the login window
            });
        } else {
            showError("Hibás felhasználónév vagy jelszó!");
        }
    }
    
    private void showRegistrationDialog() {
        int[] xMouse = {0};
        int[] yMouse = {0};
        registerDialog.panel.closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registerDialog.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerDialog.panel.closeLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerDialog.panel.closeLabel.setForeground(Constants.TEXT_COLOR);
            }
        });

        registerDialog.panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xMouse[0] = e.getX();
                yMouse[0] = e.getY();
            }
        });

        registerDialog.panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                registerDialog.setLocation(x - xMouse[0], y - yMouse[0]);
            }
        });

        // Regisztráció gomb eseménykezelője
        registerDialog.panel.registerButton.addActionListener(e -> {
            String username = registerDialog.panel.regUsernameField.getText().trim();
            String email = registerDialog.panel.emailField.getText().trim();
            String password = new String(registerDialog.panel.regPasswordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                registerDialog.panel.regErrorLabel.setText("Kérjük töltsd ki az összes mezőt!");
                Timer timer = new Timer(3000, evt -> registerDialog.panel.regErrorLabel.setText(""));
                timer.setRepeats(false);
                timer.start();
                return;
            }

            var regUser = userController.registerUser(username, password, email);
            if (regUser.isPresent()) {
                registerDialog.dispose();
                // Automatikus bejelentkezés
                SwingUtilities.invokeLater(() -> {
                    mainMenuFrame.setUser(regUser.get());
                    mainMenuFrame.setVisible(true);
                    CasinoLoginFrame.this.dispose();
                });
            } else {
                registerDialog.panel.regErrorLabel.setText("Ez a felhasználó már foglalt!");
                Timer timer = new Timer(3000, evt -> registerDialog.panel.regErrorLabel.setText(""));
                timer.setRepeats(false);
                timer.start();
            }
        });

        registerDialog.setLocationRelativeTo(this);
        registerDialog.setContentPane(registerDialog.panel);
        registerDialog.setVisible(true);
    }
    
    public void showError(String message) {
        loginPanel.errorLabel.setText(message);
        Timer timer = new Timer(3000, e -> loginPanel.errorLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
}
