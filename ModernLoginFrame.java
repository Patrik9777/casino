import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class ModernLoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserManager userManager;
    
    // Modern színek
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color SECONDARY_COLOR = new Color(100, 149, 237);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color HOVER_COLOR = new Color(65, 105, 225);

    public ModernLoginFrame() {
        userManager = new UserManager();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Modern Bejelentkezés");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        
        // Fő panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PRIMARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 0));
        
        // Bal oldali kép panel
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PRIMARY_COLOR.darker());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
            }
        };
        imagePanel.setPreferredSize(new Dimension(400, 0));
        imagePanel.setLayout(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Üdvözöljük!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        
        JLabel subtitleLabel = new JLabel("Jelentkezzen be a fiókjába");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(230, 230, 250));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(welcomeLabel, BorderLayout.NORTH);
        textPanel.add(subtitleLabel, BorderLayout.CENTER);
        
        imagePanel.add(textPanel, BorderLayout.CENTER);
        
        // Jobb oldali bejelentkezés panel
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(CARD_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
            }
        };
        loginPanel.setBackground(CARD_COLOR);
        loginPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 40, 15, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Cím
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Bejelentkezés");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(titleLabel, gbc);
        
        // Felhasználónév mező
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel userLabel = createStyledLabel("Felhasználónév");
        loginPanel.add(userLabel, gbc);
        
        gbc.gridy++;
        usernameField = createStyledTextField();
        loginPanel.add(usernameField, gbc);
        
        // Jelszó mező
        gbc.gridy++;
        JLabel passLabel = createStyledLabel("Jelszó");
        loginPanel.add(passLabel, gbc);
        
        gbc.gridy++;
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        passwordField.setPreferredSize(new Dimension(250, 40));
        loginPanel.add(passwordField, gbc);
        
        // Bejelentkezés gomb
        gbc.gridy++;
        gbc.insets = new Insets(25, 40, 15, 40);
        loginButton = createStyledButton("Bejelentkezés", PRIMARY_COLOR);
        loginButton.setPreferredSize(new Dimension(250, 45));
        loginPanel.add(loginButton, gbc);
        
        // Választóvonal
        gbc.gridy++;
        gbc.insets = new Insets(10, 40, 10, 40);
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(new Color(230, 230, 230));
        loginPanel.add(separator, gbc);
        
        // Regisztráció gomb
        gbc.gridy++;
        gbc.insets = new Insets(5, 40, 20, 40);
        registerButton = createStyledButton("Új fiók létrehozása", SECONDARY_COLOR);
        registerButton.setPreferredSize(new Dimension(250, 45));
        loginPanel.add(registerButton, gbc);
        
        // Bezárás gomb
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setForeground(Color.WHITE);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> System.exit(0));
        
        // Egér mozgás a keret áthelyezéséhez
        JPanel dragPanel = new JPanel(new BorderLayout());
        dragPanel.setOpaque(false);
        dragPanel.add(closeButton, BorderLayout.EAST);
        
        final Point[] dragStart = new Point[1];
        dragPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragStart[0] = e.getPoint();
            }
        });
        
        dragPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragStart[0] != null) {
                    Point currentLocation = getLocation();
                    int x = currentLocation.x + e.getX() - dragStart[0].x;
                    int y = currentLocation.y + e.getY() - dragStart[0].y;
                    setLocation(x, y);
                }
            }
        });
        
        mainPanel.add(dragPanel, BorderLayout.NORTH);
        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        
        // Átlátszó háttér a kerethez
        setBackground(new Color(0, 0, 0, 0));
        setContentPane(mainPanel);
        
        // Eseménykezelők
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> showRegistrationDialog());
        passwordField.addActionListener(e -> handleLogin());
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(100, 100, 100));
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        textField.setPreferredSize(new Dimension(250, 40));
        return textField;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(HOVER_COLOR);
                } else {
                    g2.setColor(bgColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };
        
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showError("Kérjük, töltsön ki minden mezőt!");
            return;
        }

        if (userManager.login(username, password)) {
            showSuccess("Sikeres bejelentkezés!\nÜdvözöljük " + username + "!");
            // Itt lehetne továbbmenni a főalkalmazásba
            // new MainApplicationFrame(userManager).setVisible(true);
            // dispose();
        } else {
            showError("Hibás felhasználónév vagy jelszó!");
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, 
            "<html><div style='text-align: center;'>" + message + "</div></html>", 
            "Hiba", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, 
            "<html><div style='text-align: center;'>" + message + "</div></html>", 
            "Sikeres művelet", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRegistrationDialog() {
        JTextField regUsernameField = new JTextField();
        JPasswordField regPasswordField = new JPasswordField();
        JTextField regEmailField = new JTextField();
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JLabel titleLabel = new JLabel("Regisztráció");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(createStyledLabel("Felhasználónév:"), gbc);
        
        gbc.gridy++;
        regUsernameField = createStyledTextField();
        gbc.gridwidth = 2;
        panel.add(regUsernameField, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(createStyledLabel("Jelszó:"), gbc);
        
        gbc.gridy++;
        regPasswordField = new JPasswordField();
        regPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        regPasswordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        regPasswordField.setPreferredSize(new Dimension(250, 40));
        gbc.gridwidth = 2;
        panel.add(regPasswordField, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(createStyledLabel("E-mail:"), gbc);
        
        gbc.gridy++;
        regEmailField = createStyledTextField();
        gbc.gridwidth = 2;
        panel.add(regEmailField, gbc);
        
        int result = JOptionPane.showOptionDialog(
            this, 
            panel, 
            "Regisztráció", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE,
            null,
            new Object[]{"Regisztráció", "Mégse"},
            "Regisztráció"
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = regUsernameField.getText();
            String password = new String(regPasswordField.getPassword());
            String email = regEmailField.getText();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                showError("Kérjük, töltsön ki minden mezőt!");
                return;
            }

            if (userManager.registerUser(username, password, email)) {
                showSuccess("Sikeres regisztráció!\nMost már bejelentkezhet.");
            } else {
                showError("A felhasználónév már foglalt!");
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Modern kinézet beállítása
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Egyedi ablak dekoráció letiltása a jobb megjelenés érdekében
            JFrame.setDefaultLookAndFeelDecorated(false);
            
            // Betűtípus simítása
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            
            // Ablak megjelenítése
            SwingUtilities.invokeLater(() -> {
                ModernLoginFrame frame = new ModernLoginFrame();
                frame.setVisible(true);
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
