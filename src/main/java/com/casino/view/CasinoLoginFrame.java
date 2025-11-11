package com.casino.view;

import com.casino.models.User;
import com.casino.services.UserService;
import com.casino.view.buttons.CommonFancyButton;
import com.casino.view.dialogs.RegisterDialog;
import com.casino.view.panels.LoginPanel;
import com.casino.view.panels.LoginRightPanel;
import com.casino.view.panels.ToRegisterPanel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import static com.casino.helpers.SwigHelper.drawCornerDecoration;
import static com.casino.helpers.SwigHelper.styleTextField;

@Controller
@AllArgsConstructor
@Getter
public class CasinoLoginFrame extends JFrame {

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterDialog registerDialog;

    private ToRegisterPanel toRegisterPanel;
    private LoginPanel loginPanel;
    private LoginRightPanel loginRightPanel;


    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private CommonFancyButton loginButton;
    private JLabel registerLink;
    private JLabel closeLabel;
    private JLabel errorLabel;
    private int xMouse, yMouse;



    public CasinoLoginFrame() {
        setUndecorated(true);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                
                // Gradiens háttér
                GradientPaint gradient = new GradientPaint(0, 0, new Color(10, 10, 15), 0, getHeight(), new Color(30, 25, 35));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Bal oldali panel arany kerettel
                g2d.setColor(Constants.PANEL_COLOR);
                g2d.fillRoundRect(0, 0, getWidth() / 2, getHeight(), 0, 0);
                
                // Arany keret a bal oldali panel körül
                g2d.setColor(Constants.GOLD_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth() / 2 - 2, getHeight() - 2, 0, 0);
                
                // Finom mintázat a háttérben
                g2d.setColor(new Color(218, 165, 32, 10));
                for (int i = 0; i < getWidth(); i += 40) {
                    for (int j = 0; j < getHeight(); j += 40) {
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
                drawCornerDecoration(g2d, getWidth()/2 - 100, 20, 80, 80, false);
                
                g2d.dispose();
            }
        };
        
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 0, 0, 0));
        
        // Bezárás gomb
        closeLabel = new JLabel("×");
        closeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        closeLabel.setForeground(Constants.TEXT_COLOR);
        closeLabel.setBounds(getWidth() - 50, 10, 30, 30);
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        
        // Felhasználónév mező
        JLabel userLabel = new JLabel("Felhasználónév");
        userLabel.setForeground(Constants.TEXT_COLOR);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setBounds(50, 150, 200, 20);
        
        usernameField = new JTextField();
        styleTextField(usernameField);
        usernameField.setBounds(50, 175, 300, 40);
        
        // Jelszó mező
        JLabel passLabel = new JLabel("Jelszó");
        passLabel.setForeground(Constants.TEXT_COLOR);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passLabel.setBounds(50, 235, 200, 20);
        
        passwordField = new JPasswordField();
        styleTextField(passwordField);
        passwordField.setBounds(50, 260, 300, 40);
        
        // Bejelentkezés gomb
        loginButton = new CommonFancyButton();
        loginButton.setText("BEJELENTKEZÉS");
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(50, 330, 300, 45);
        
        // Gomb hover effekt
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
        
        // Regisztráció link
        JLabel regText = new JLabel("Nincs még fiókod? ");
        regText.setForeground(Constants.TEXT_COLOR);
        regText.setFont(new Font("Arial", Font.PLAIN, 12));
        
        registerLink = new JLabel("Regisztrálj most!");
        registerLink.setForeground(Constants.LINK_COLOR);
        registerLink.setFont(new Font("Arial", Font.BOLD, 12));
        registerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JPanel regPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        regPanel.setOpaque(false);
        regPanel.add(regText);
        regPanel.add(registerLink);
        regPanel.setBounds(50, 390, 300, 20);
        
        // Error label
        errorLabel = new JLabel("");
        errorLabel.setForeground(new Color(220, 50, 50));
        errorLabel.setFont(new Font("Arial", Font.BOLD, 13));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBounds(50, 350, 300, 30);
        
        // Jobb oldali panel díszítésekkel
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                
                // Sötét háttér finom mintázattal
                g2d.setColor(new Color(15, 15, 20));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Finom rácsmintázat
                g2d.setColor(new Color(218, 165, 32, 5));
                for (int i = 0; i < getWidth(); i += 30) {
                    g2d.drawLine(i, 0, i, getHeight());
                }
                for (int i = 0; i < getHeight(); i += 30) {
                    g2d.drawLine(0, i, getWidth(), i);
                }
                
                // Középső körök díszítésként
                g2d.setColor(new Color(218, 165, 32, 10));
                g2d.fillOval(50, 50, 150, 150);
                g2d.setColor(new Color(218, 165, 32, 15));
                g2d.fillOval(200, 200, 200, 200);
                g2d.setColor(new Color(218, 165, 32, 20));
                g2d.fillOval(100, 350, 180, 180);
                
                // Középre igazított üdvözlő szöveg
                g2d.setFont(new Font("Arial", Font.BOLD, 36));
                String welcome = "ÜDVÖZÖLJÜK!";
                int welcomeWidth = g2d.getFontMetrics().stringWidth(welcome);
                
                // Szöveg árnyéka
                g2d.setColor(new Color(0, 0, 0, 120));
                g2d.drawString(welcome, (getWidth() - welcomeWidth) / 2 + 2, getHeight() / 2 + 2);
                
                // Arany színű szöveg gradiensekkel
                GradientPaint goldGradient = new GradientPaint(0, 0, Constants.GOLD_LIGHT, 0, 40, Constants.GOLD_DARK);
                g2d.setPaint(goldGradient);
                g2d.drawString(welcome, (getWidth() - welcomeWidth) / 2, getHeight() / 2);
                
                // Aláíró vonal
                g2d.setColor(Constants.GOLD_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                int lineY = getHeight() / 2 + 20;
                g2d.drawLine((getWidth() - welcomeWidth) / 2 - 20, lineY, 
                            (getWidth() + welcomeWidth) / 2 + 20, lineY);
                
                // Díszelemek a sarkokban
                drawCornerDecoration(g2d, 20, getHeight() - 100, 80, 80, false);
                drawCornerDecoration(g2d, getWidth() - 100, getHeight() - 100, 80, 80, true);
                
                g2d.dispose();
            }
        };
        rightPanel.setBounds(getWidth() / 2, 0, getWidth() / 2, getHeight());
        rightPanel.setBackground(new Color(0, 0, 0, 0));
        
        // Komponensek hozzáadása
        mainPanel.add(closeLabel);
        mainPanel.add(userLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(errorLabel);
        mainPanel.add(regPanel);
        mainPanel.add(rightPanel);
        
        // Eseménykezelők
        loginButton.addActionListener(e -> login());
        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showRegistrationDialog();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                registerLink.setForeground(Constants.LINK_COLOR.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                registerLink.setForeground(Constants.LINK_COLOR);
            }
        });
        
        // Húzás a címsor nélküli ablakhoz
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY();
            }
        });
        
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - xMouse, y - yMouse);
            }
        });
        
        add(mainPanel);
    }
    
    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Kérjük töltsd ki mindkét mezőt!");
            return;
        }
        
        java.util.Optional<User> userOpt = userService.authenticateUser(username, password);
        if (userOpt.isPresent()) {
            // Open main menu in the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                MainMenuFrame mainMenu = new MainMenuFrame(userOpt.get());
                mainMenu.setVisible(true);
                this.dispose(); // Close the login window
            });
        } else {
            showError("Hibás felhasználónév vagy jelszó!");
        }
    }
    
    private void showRegistrationDialog() {
        /*
        // Regisztráció gomb eseménykezelője
        registerButton.addActionListener(e -> {
            String username = regUsernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(regPasswordField.getPassword());
            
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                regErrorLabel.setText("Kérjük töltsd ki az összes mezőt!");
                Timer timer = new Timer(3000, evt -> regErrorLabel.setText(""));
                timer.setRepeats(false);
                timer.start();
                return;
            }

            var regedUser = userService.registerUser(username, password, email);
            if (regedUser.isPresent()) {
                dialog.dispose();
                // Automatikus bejelentkezés
                SwingUtilities.invokeLater(() -> {
                    MainMenuFrame mainMenu = new MainMenuFrame(regedUser.get());
                    mainMenu.setVisible(true);
                    CasinoLoginFrame.this.dispose();
                });
            } else {
                regErrorLabel.setText("Ez a felhasználónév már foglalt!");
                Timer timer = new Timer(3000, evt -> regErrorLabel.setText(""));
                timer.setRepeats(false);
                timer.start();
            }
        });
        */
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

        registerDialog.setLocationRelativeTo(this);
        registerDialog.setContentPane(registerDialog.panel);
        registerDialog.setVisible(true);
    }
    
    public void showError(String message) {
        errorLabel.setText(message);
        Timer timer = new Timer(3000, e -> errorLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
}
