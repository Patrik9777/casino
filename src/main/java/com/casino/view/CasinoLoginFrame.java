package com.casino.view;

import com.casino.model.User;
import com.casino.service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class CasinoLoginFrame extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(20, 20, 25);
    private static final Color PANEL_COLOR = new Color(30, 30, 35);
    private static final Color TEXT_COLOR = new Color(230, 200, 150);
    private static final Color GOLD_COLOR = new Color(218, 165, 32);
    private static final Color GOLD_LIGHT = new Color(255, 215, 0);
    private static final Color GOLD_DARK = new Color(184, 134, 11);
    private static final Color BUTTON_COLOR = new Color(218, 165, 32);
    private static final Color BUTTON_HOVER = new Color(255, 215, 0);
    private static final Color BUTTON_PRESSED = new Color(184, 134, 11);
    private static final Color FIELD_BG = new Color(40, 40, 45);
    private static final Color FIELD_BORDER = new Color(100, 90, 70);
    private static final Color LINK_COLOR = new Color(218, 165, 32);
    private static final BasicStroke THIN_STROKE = new BasicStroke(1.5f);
    private static final BasicStroke MEDIUM_STROKE = new BasicStroke(2.5f);
    private static final float[] DASH_PATTERN = {5, 5};

    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel registerLink;
    private JLabel closeLabel;
    private JLabel errorLabel;
    private int xMouse, yMouse;

    private UserService userService = UserService.getInstance();

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
                g2d.setColor(PANEL_COLOR);
                g2d.fillRoundRect(0, 0, getWidth() / 2, getHeight(), 0, 0);
                
                // Arany keret a bal oldali panel körül
                g2d.setColor(GOLD_COLOR);
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
                GradientPaint goldGradient = new GradientPaint(0, 0, GOLD_LIGHT, 0, 30, GOLD_DARK);
                g2d.setPaint(goldGradient);
                g2d.drawString(title, 50, 80);
                
                // Aláhúzás díszítéssel
                g2d.setColor(GOLD_COLOR);
                g2d.fillRoundRect(50, 90, titleWidth, 4, 2, 2);
                
                // Finom vonalak a cím alatt
                g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, DASH_PATTERN, 0));
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
        closeLabel.setForeground(TEXT_COLOR);
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
                closeLabel.setForeground(TEXT_COLOR);
            }
        });
        
        // Felhasználónév mező
        JLabel userLabel = new JLabel("Felhasználónév");
        userLabel.setForeground(TEXT_COLOR);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setBounds(50, 150, 200, 20);
        
        usernameField = new JTextField();
        styleTextField(usernameField);
        usernameField.setBounds(50, 175, 300, 40);
        
        // Jelszó mező
        JLabel passLabel = new JLabel("Jelszó");
        passLabel.setForeground(TEXT_COLOR);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passLabel.setBounds(50, 235, 200, 20);
        
        passwordField = new JPasswordField();
        styleTextField(passwordField);
        passwordField.setBounds(50, 260, 300, 40);
        
        // Bejelentkezés gomb
        loginButton = new JButton("BEJELENTKEZÉS") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                
                // Gomb kitöltése
                if (getModel().isPressed()) {
                    // Nyomott állapot
                    GradientPaint gradient = new GradientPaint(0, 0, GOLD_DARK, 0, getHeight(), new Color(139, 101, 8));
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                } else if (getModel().isRollover()) {
                    // Egér felette
                    GradientPaint gradient = new GradientPaint(0, 0, GOLD_LIGHT, 0, getHeight(), GOLD_COLOR);
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                } else {
                    // Normál állapot
                    GradientPaint gradient = new GradientPaint(0, 0, GOLD_COLOR, 0, getHeight(), GOLD_DARK);
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
                
                // Szegély
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 8, 8);
                
                // Szöveg középre igazítása
                FontMetrics fm = g2d.getFontMetrics();
                String text = getText();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                
                // Szöveg árnyéka
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(text, x+1, y+1);
                
                // Fő szöveg
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString(text, x, y);
                
                // Fényhatás a gomb tetején
                if (!getModel().isPressed()) {
                    g2d.setColor(new Color(255, 255, 255, 60));
                    g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()/2, 8, 8);
                }
                
                g2d.dispose();
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // Ne legyen alapértelmezett szegély
            }
        };
        
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
        regText.setForeground(TEXT_COLOR);
        regText.setFont(new Font("Arial", Font.PLAIN, 12));
        
        registerLink = new JLabel("Regisztrálj most!");
        registerLink.setForeground(LINK_COLOR);
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
                GradientPaint goldGradient = new GradientPaint(0, 0, GOLD_LIGHT, 0, 40, GOLD_DARK);
                g2d.setPaint(goldGradient);
                g2d.drawString(welcome, (getWidth() - welcomeWidth) / 2, getHeight() / 2);
                
                // Aláíró vonal
                g2d.setColor(GOLD_COLOR);
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
                registerLink.setForeground(LINK_COLOR.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                registerLink.setForeground(LINK_COLOR);
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
    
    private void drawCornerDecoration(Graphics2D g2d, int x, int y, int size, int thickness, boolean topLeft) {
        g2d.setStroke(new BasicStroke(2f));
        g2d.setColor(GOLD_COLOR);
        
        int arc = size / 2;
        
        // Külső sarok
        if (topLeft) {
            // Bal felső sarok
            g2d.drawLine(x, y + arc, x, y);
            g2d.drawLine(x, y, x + arc, y);
            
            // Belső vonalak
            int innerOffset = 10;
            g2d.drawLine(x + innerOffset, y + arc, x + innerOffset, y + innerOffset);
            g2d.drawLine(x + innerOffset, y + innerOffset, x + arc, y + innerOffset);
        } else {
            // Jobb felső sarok
            g2d.drawLine(x + size - arc, y, x + size, y);
            g2d.drawLine(x + size, y, x + size, y + arc);
            
            // Belső vonalak
            int innerOffset = 10;
            g2d.drawLine(x + size - innerOffset, y + innerOffset, x + size - innerOffset, y + innerOffset);
            g2d.drawLine(x + size - innerOffset, y + innerOffset, x + size - arc + innerOffset, y + innerOffset);
        }
    }
    
    private void styleTextField(JTextField field) {
        field.setBackground(FIELD_BG);
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FIELD_BORDER, 1),
            BorderFactory.createEmptyBorder(0, 15, 0, 15)
        ));
        field.setCaretColor(GOLD_LIGHT);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setSelectionColor(new Color(218, 165, 32, 100));
        field.setSelectedTextColor(Color.WHITE);
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
        JDialog dialog = new JDialog(this, "Regisztráció", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        dialog.setShape(new RoundRectangle2D.Double(0, 0, 400, 400, 20, 20));
        
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(PANEL_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Cím
                g2d.setColor(TEXT_COLOR);
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                String title = "REGISZTRÁCIÓ";
                int titleWidth = g2d.getFontMetrics().stringWidth(title);
                g2d.drawString(title, (getWidth() - titleWidth) / 2, 40);
                
                g2d.dispose();
            }
        };
        
        // Bezárás gomb
        JLabel closeLabel = new JLabel("×");
        closeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        closeLabel.setForeground(TEXT_COLOR);
        closeLabel.setBounds(360, 5, 30, 30);
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.dispose();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(TEXT_COLOR);
            }
        });
        
        // Mezők
        JLabel userLabel = new JLabel("Felhasználónév");
        userLabel.setForeground(TEXT_COLOR);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setBounds(50, 70, 300, 20);
        
        JTextField regUsernameField = new JTextField();
        styleTextField(regUsernameField);
        regUsernameField.setBounds(50, 90, 300, 35);
        
        JLabel emailLabel = new JLabel("E-mail cím");
        emailLabel.setForeground(TEXT_COLOR);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailLabel.setBounds(50, 135, 300, 20);
        
        JTextField emailField = new JTextField();
        styleTextField(emailField);
        emailField.setBounds(50, 155, 300, 35);
        
        JLabel passLabel = new JLabel("Jelszó");
        passLabel.setForeground(TEXT_COLOR);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passLabel.setBounds(50, 200, 300, 20);
        
        JPasswordField regPasswordField = new JPasswordField();
        styleTextField(regPasswordField);
        regPasswordField.setBounds(50, 220, 300, 35);
        
        // Error label a regisztrációs dialogban
        JLabel regErrorLabel = new JLabel("");
        regErrorLabel.setForeground(new Color(220, 50, 50));
        regErrorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        regErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        regErrorLabel.setBounds(50, 330, 300, 30);
        
        // Regisztráció gomb
        JButton registerButton = new JButton("REGISZTRÁCIÓ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(BUTTON_PRESSED);
                } else if (getModel().isRollover()) {
                    g2d.setColor(BUTTON_HOVER);
                } else {
                    g2d.setColor(BUTTON_COLOR);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString(getText(), x, y);
                
                g2d.dispose();
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // Ne legyen szegély
            }
        };
        
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBounds(50, 280, 300, 40);
        
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
            
            if (userService.registerUser(username, password, email)) {
                dialog.dispose();
                // Automatikus bejelentkezés
                java.util.Optional<User> userOpt = userService.authenticateUser(username, password);
                if (userOpt.isPresent()) {
                    SwingUtilities.invokeLater(() -> {
                        MainMenuFrame mainMenu = new MainMenuFrame(userOpt.get());
                        mainMenu.setVisible(true);
                        CasinoLoginFrame.this.dispose();
                    });
                }
            } else {
                regErrorLabel.setText("Ez a felhasználónév már foglalt!");
                Timer timer = new Timer(3000, evt -> regErrorLabel.setText(""));
                timer.setRepeats(false);
                timer.start();
            }
        });
        
        // Komponensek hozzáadása
        panel.add(closeLabel);
        panel.add(userLabel);
        panel.add(regUsernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passLabel);
        panel.add(regPasswordField);
        panel.add(registerButton);
        panel.add(regErrorLabel);
        
        // Húzás
        int[] xMouse = {0};
        int[] yMouse = {0};
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xMouse[0] = e.getX();
                yMouse[0] = e.getY();
            }
        });
        
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                dialog.setLocation(x - xMouse[0], y - yMouse[0]);
            }
        });
        
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        Timer timer = new Timer(3000, e -> errorLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
    
    public static void main(String[] args) {
        try {
            // Nimbus kinézet beállítása
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Ha Nimbus nem elérhető, használja az alapértelmezettet
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        // Ablak létrehozása és megjelenítése
        SwingUtilities.invokeLater(() -> {
            CasinoLoginFrame frame = new CasinoLoginFrame();
            frame.setVisible(true);
        });
    }
}
