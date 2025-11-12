import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserManager userManager;

    public LoginFrame() {
        userManager = new UserManager();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bejelentkezés");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Panel a komponenseknek
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Felhasználónév címke és mező
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Felhasználónév:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Jelszó címke és mező
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Jelszó:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Bejelentkezés gomb
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        loginButton = new JButton("Bejelentkezés");
        panel.add(loginButton, gbc);

        // Regisztráció gomb
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerButton = new JButton("Regisztráció");
        panel.add(registerButton, gbc);

        // Eseménykezelők
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> showRegistrationDialog());

        // Enter lenyomásának kezelése
        passwordField.addActionListener(e -> handleLogin());

        add(panel);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kérjük, töltsön ki minden mezőt!", "Hiba", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userManager.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Sikeres bejelentkezés!\nÜdvözöljük " + username + "!", "Sikeres bejelentkezés", JOptionPane.INFORMATION_MESSAGE);
            // Itt lehetne továbbmenni a főalkalmazásba
            // new MainApplicationFrame(userManager).setVisible(true);
            // dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Hibás felhasználónév vagy jelszó!", "Bejelentkezési hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegistrationDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Felhasználónév:"));
        panel.add(usernameField);
        panel.add(new JLabel("Jelszó:"));
        panel.add(passwordField);
        panel.add(new JLabel("E-mail:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Regisztráció", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kérjük, töltsön ki minden mezőt!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userManager.registerUser(username, password, email)) {
                JOptionPane.showMessageDialog(this, "Sikeres regisztráció! Most már bejelentkezhet.", "Sikeres regisztráció", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "A felhasználónév már foglalt!", "Regisztrációs hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Nimbus kinézet beállítása
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // Ha Nimbus nem elérhető, használjuk az alapértelmezettet
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
