package com.casino.view.panels;

import com.casino.view.Constants;
import com.casino.view.buttons.CommonFancyButton;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.casino.helpers.SwigHelper.drawCornerDecoration;
import static com.casino.helpers.SwigHelper.styleTextField;

@Component
@Getter
public class LoginPanel extends JPanel {

    @Autowired
    public LoginRightPanel rightPanel;

    @Autowired
    public ToRegisterPanel toRegisterPanel;

    public JLabel closeLabel;
    public JLabel userLabel;
    public JTextField usernameField;
    public JLabel passLabel;
    public JPasswordField passwordField;
    public CommonFancyButton loginButton;
    public JLabel errorLabel;


    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
        logic();
    }

    private void setUp() {
        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        // Now parent/container/root exists
        var root = SwingUtilities.getRoot(this);
        if (root != null) {
            int width = root.getWidth();
            int height = root.getHeight();
            closeLabel.setBounds(width - 50, 10, 30, 30);
        }
    }

    private void initComponents() {
        closeLabel = new JLabel("×");
        closeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        closeLabel.setForeground(Constants.TEXT_COLOR);
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(closeLabel);

        userLabel = new JLabel("Felhasználónév");
        userLabel.setForeground(Constants.TEXT_COLOR);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setBounds(50, 150, 200, 20);
        add(userLabel);

        usernameField = new JTextField();
        styleTextField(usernameField);
        usernameField.setBounds(50, 175, 300, 40);
        add(usernameField);

        passLabel = new JLabel("Jelszó");
        passLabel.setForeground(Constants.TEXT_COLOR);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passLabel.setBounds(50, 235, 200, 20);
        add(passLabel);

        passwordField = new JPasswordField();
        styleTextField(passwordField);
        passwordField.setBounds(50, 260, 300, 40);
        add(passwordField);

        loginButton = new CommonFancyButton();
        loginButton.setText("BEJELENTKEZÉS");
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(50, 330, 300, 45);
        add(loginButton);

        errorLabel = new JLabel("");
        errorLabel.setForeground(new Color(220, 50, 50));
        errorLabel.setFont(new Font("Arial", Font.BOLD, 13));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBounds(50, 350, 300, 30);
        add(errorLabel);

        add(rightPanel);
        add(toRegisterPanel);
    }

    private void logic() {
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setForeground(new Color(255, 255, 255, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setForeground(Color.WHITE);
            }
        });
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(Constants.TEXT_COLOR);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var root = SwingUtilities.getRoot(this);
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        // Gradiens háttér
        GradientPaint gradient = new GradientPaint(0, 0, new Color(10, 10, 15), 0, height, new Color(30, 25, 35));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);

        // Bal oldali panel arany kerettel
        g2d.setColor(Constants.PANEL_COLOR);
        g2d.fillRoundRect(0, 0, width / 2, height, 0, 0);

        // Arany keret a bal oldali panel körül
        g2d.setColor(Constants.GOLD_COLOR);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(1, 1, width / 2 - 2, height - 2, 0, 0);

        // Finom mintázat a háttérben
        g2d.setColor(new Color(218, 165, 32, 10));
        for (int i = 0; i < width; i += 40) {
            for (int j = 0; j < height; j += 40) {
                g2d.fillOval(i, j, 2, 2);
            }
        }

        // Cím szöveg arany színnel és árnyékkal
        g2d.setFont(new Font("Arial", Font.BOLD, 32));
        String title = "GYPSY FEELING";
        int titleWidth = g2d.getFontMetrics().stringWidth(title);

        // Szöveg árnyéka
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(title, 52, 82);

        // Fő szöveg arany színnel
        GradientPaint goldGradient = new GradientPaint(0, 0, Constants.GOLD_LIGHT, 0, 30, Constants.GOLD_DARK);
        g2d.setPaint(goldGradient);
        g2d.drawString(title, 50, 80);

        // Aláhúzás díszítéssel
        g2d.setColor(Constants.GOLD_COLOR);
        g2d.fillRoundRect(50, 90, titleWidth, 4, 2, 2);

        // Finom vonalak a cím alatt
        g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, Constants.DASH_PATTERN, 0));
        g2d.setColor(new Color(218, 165, 32, 100));
        g2d.drawLine(50, 100, 50 + titleWidth, 100);

        // Finom díszítések a sarkokban
        drawCornerDecoration(g2d, 20, 20, 80, 80, true);
        drawCornerDecoration(g2d, width / 2 - 100, 20, 80, 80, false);

        g2d.dispose();
    }
}
