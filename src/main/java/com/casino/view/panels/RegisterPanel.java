package com.casino.view.panels;

import com.casino.view.Constants;
import com.casino.view.buttons.CommonFancyButton;
import jakarta.annotation.PostConstruct;

import javax.swing.*;
import java.awt.*;

import lombok.Getter;
import org.springframework.stereotype.Component;

import static com.casino.helpers.SwigHelper.styleTextField;

@Component
@Getter
public class RegisterPanel extends JPanel {
    public JLabel closeLabel;
    public JLabel userLabel;
    public JTextField regUsernameField;
    public JLabel emailLabel;
    public JTextField emailField;
    public JLabel passLabel;
    public JPasswordField regPasswordField;
    public JLabel regErrorLabel;
    public CommonFancyButton registerButton;

    @PostConstruct
    private void initPanel() {
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        closeLabel = new JLabel("×");
        closeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        closeLabel.setForeground(Constants.TEXT_COLOR);
        closeLabel.setBounds(360, 5, 30, 30);
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(closeLabel);

        userLabel = new JLabel("Felhasználónév");
        userLabel.setForeground(Constants.TEXT_COLOR);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setBounds(50, 70, 300, 20);
        add(userLabel);

        regUsernameField = new JTextField();
        styleTextField(regUsernameField);
        regUsernameField.setBounds(50, 90, 300, 35);
        add(regUsernameField);

        emailLabel = new JLabel("E-mail cím");
        emailLabel.setForeground(Constants.TEXT_COLOR);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailLabel.setBounds(50, 135, 300, 20);
        add(emailLabel);

        emailField = new JTextField();
        styleTextField(emailField);
        emailField.setBounds(50, 155, 300, 35);
        add(emailField);

        passLabel = new JLabel("Jelszó");
        passLabel.setForeground(Constants.TEXT_COLOR);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passLabel.setBounds(50, 200, 300, 20);
        add(passLabel);

        regPasswordField = new JPasswordField();
        styleTextField(regPasswordField);
        regPasswordField.setBounds(50, 220, 300, 35);
        add(regPasswordField);

        // Error label a regisztrációs dialogban
        regErrorLabel = new JLabel("");
        regErrorLabel.setForeground(new Color(220, 50, 50));
        regErrorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        regErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        regErrorLabel.setBounds(50, 330, 300, 30);
        add(regErrorLabel);

        registerButton = new CommonFancyButton();
        registerButton.setText("REGISZTRÁCIÓ");
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBounds(50, 280, 300, 40);
        add(registerButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Constants.PANEL_COLOR);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Cím
        g2d.setColor(Constants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String title = "REGISZTRÁCIÓ";
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, (getWidth() - titleWidth) / 2, 40);

        g2d.dispose();
    }
}
