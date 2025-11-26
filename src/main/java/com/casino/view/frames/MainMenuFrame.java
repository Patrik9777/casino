package com.casino.view.frames;

import com.casino.view.CrashGame;
import com.casino.view.LottoGame;
import com.casino.view.RouletteGame;
import com.casino.view.dialogs.*;
import com.casino.Main;
import com.casino.models.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Component
@Getter
public class MainMenuFrame extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(20, 20, 25);
    private static final Color PANEL_COLOR = new Color(30, 30, 35);
    private static final Color SIDEBAR_COLOR = new Color(25, 25, 30);
    private static final Color TEXT_COLOR = new Color(230, 200, 150);
    private static final Color GOLD_COLOR = new Color(218, 165, 32);
    private static final Color GOLD_LIGHT = new Color(255, 215, 0);
    private static final Color GOLD_DARK = new Color(184, 134, 11);
    private static final Color BUTTON_COLOR = new Color(218, 165, 32);
    private static final Color BUTTON_HOVER = new Color(255, 215, 0);
    private static final Color BUTTON_PRESSED = new Color(184, 134, 11);
    private static final Color FIELD_BG = new Color(40, 40, 45);
    private static final Color FIELD_BORDER = new Color(100, 90, 70);
    
    private User currentUser;
    private JLabel balanceLabel;
    private JLabel userLabel;
    private JPanel contentPanel;
    private Map<String, Integer> couponCodes;

    @Autowired
    private BlackjackGameDialog blackjackGameDialog;

    @PostConstruct
    private void initPanel() {
        initializeCouponCodes();
        initializeUI();
    }

    public void setUser(User user) {
        currentUser = user;
        userLabel.setText("Üdv, " + currentUser.username + "!");
        balanceLabel.setText("Egyenleg: $" + currentUser.balance);
    }
    
    private void initializeCouponCodes() {
        couponCodes = new HashMap<>();
        couponCodes.put("GYPSY10", 10);
        couponCodes.put("MARIJO50", 50);
        couponCodes.put("LAKATOS_NARUTO250", 250);
    }
    
    private void initializeUI() {
        setTitle("Casino - Főmenü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_COLOR, getWidth(), getHeight(), new Color(30, 20, 35));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(new Color(255, 215, 0, 30));
                for (int i = 0; i < 50; i++) {
                    int x = (i * 137) % getWidth();
                    int y = (i * 211) % getHeight();
                    int size = 2 + (i % 4);
                    g2d.fillOval(x, y, size, size);
                }
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel sidebarPanel = createSidebarPanel();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout());
        showWelcomeContent();
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        //setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        
        JLabel welcomeLabel = new JLabel("🎰 GYPSY FEELING 🎰");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 42));
        welcomeLabel.setForeground(GOLD_LIGHT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setOpaque(false);
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        
        userLabel = new JLabel("Üdv, _ !");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        userLabel.setForeground(TEXT_COLOR);
        userLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        balanceLabel = new JLabel("Egyenleg: $0");
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        balanceLabel.setForeground(GOLD_COLOR);
        balanceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        userInfoPanel.add(userLabel);
        userInfoPanel.add(Box.createVerticalStrut(5));
        userInfoPanel.add(balanceLabel);
        
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);
        headerPanel.add(userInfoPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(SIDEBAR_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        String[] menuItems = {"👤 Profil", "💰 Befizetés", "💸 Kifizetés", "🎴 Blackjack", "🎲 Rulett", "💥 Crash", "🎟️ Lotto", "🔓 Kijelentkezés", "🚪 Kilépés"};
        
        for (String item : menuItems) {
            JButton menuButton = createSidebarButton(item);
            sidebarPanel.add(menuButton);
            sidebarPanel.add(Box.createVerticalStrut(10));
        }
        
        return sidebarPanel;
    }
    
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(BUTTON_PRESSED);
                } else if (getModel().isRollover()) {
                    g2d.setColor(BUTTON_HOVER);
                } else {
                    g2d.setColor(BUTTON_COLOR);
                }
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                g2d.setColor(BACKGROUND_COLOR);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = 15;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(230, 50));
        button.setMaximumSize(new Dimension(230, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> handleMenuAction(text));
        
        return button;
    }
    
    private void handleMenuAction(String menuItem) {
        if (menuItem.contains("Profil")) {
            showProfileContent();
        } else if (menuItem.contains("Befizetés")) {
            showDepositContent();
        } else if (menuItem.contains("Kifizetés")) {
            showWithdrawContent();
        } else if (menuItem.contains("Blackjack")) {
            showBlackjackGame();
        } else if (menuItem.contains("Rulett")) {
            showRouletteGame();
        } else if (menuItem.contains("Crash")) {
            showCrashGame();
        } else if (menuItem.contains("Lotto")) {
            showLottoGame();
        } else if (menuItem.contains("Kijelentkezés")) {
            int choice = JOptionPane.showConfirmDialog(this, "Biztosan ki szeretnél jelentkezni?", "Kijelentkezés", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                this.dispose();
                SwingUtilities.invokeLater(() -> Main.loginFrame.setVisible(true));
            }
        } else if (menuItem.contains("Kilépés")) {
            int choice = JOptionPane.showConfirmDialog(this, "Biztosan ki szeretnél lépni?", "Kilépés", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    private void showWelcomeContent() {
        contentPanel.removeAll();
        JPanel welcomePanel = new JPanel();
        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new GridBagLayout());
        
        JPanel gamesGrid = new JPanel();
        gamesGrid.setOpaque(false);
        gamesGrid.setLayout(new GridLayout(2, 2, 30, 30));
        gamesGrid.setPreferredSize(new Dimension(800, 600));
        
        String[] games = {"🎴 Blackjack", "🎲 Rulett", "💥 Crash", "🎟️ Lotto"};
        for (String game : games) {
            JButton gameBlock = createGameBlock(game);
            gamesGrid.add(gameBlock);
        }
        
        welcomePanel.add(gamesGrid);
        contentPanel.add(welcomePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showProfileContent() {
        contentPanel.removeAll();
        JPanel profilePanel = new JPanel();
        profilePanel.setOpaque(false);
        profilePanel.setLayout(new BorderLayout());
        
        JButton closeButton = createCloseButton();
        closeButton.addActionListener(e -> showWelcomeContent());
        JPanel closePanel = new JPanel();
        closePanel.setOpaque(false);
        closePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        closePanel.add(closeButton);
        profilePanel.add(closePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());
        JPanel formPanel = new JPanel();
        formPanel.setBackground(PANEL_COLOR);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        formPanel.setPreferredSize(new Dimension(500, 400));
        JLabel titleLabel = new JLabel("👤 Profil Beállítások");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(GOLD_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(30));
        JLabel nicknameLabel = new JLabel("Becenév:");
        nicknameLabel.setForeground(TEXT_COLOR);
        nicknameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JTextField nicknameField = createStyledTextField(currentUser.username);
        formPanel.add(nicknameLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(nicknameField);
        formPanel.add(Box.createVerticalStrut(20));
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(TEXT_COLOR);
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JTextField emailField = createStyledTextField(currentUser.email);
        emailField.setEditable(false);
        formPanel.add(emailLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(30));
        JButton saveButton = createStyledButton("Mentés", BUTTON_COLOR);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> {
            String newNickname = nicknameField.getText().trim();
            if (!newNickname.isEmpty()) {
                currentUser.username = newNickname;

                userLabel.setText("Üdv, " + currentUser.username + "!");
                JOptionPane.showMessageDialog(this, "Profil sikeresen frissítve!", "Siker", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "A becenév nem lehet üres!", "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        });
        formPanel.add(saveButton);
        centerPanel.add(formPanel);
        profilePanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(profilePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showDepositContent() {
        contentPanel.removeAll();
        JPanel depositPanel = new JPanel();
        depositPanel.setOpaque(false);
        depositPanel.setLayout(new BorderLayout());
        
        JButton closeButton = createCloseButton();
        closeButton.addActionListener(e -> showWelcomeContent());
        JPanel closePanel = new JPanel();
        closePanel.setOpaque(false);
        closePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        closePanel.add(closeButton);
        depositPanel.add(closePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());
        JPanel formPanel = new JPanel();
        formPanel.setBackground(PANEL_COLOR);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        formPanel.setPreferredSize(new Dimension(600, 700));
        JLabel titleLabel = new JLabel("💰 Befizetés");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(GOLD_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(25));
        JLabel cardNumberLabel = new JLabel("Kártyaszám (20 számjegy):");
        cardNumberLabel.setForeground(TEXT_COLOR);
        cardNumberLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        formPanel.add(cardNumberLabel);
        formPanel.add(Box.createVerticalStrut(10));
        JTextField cardNumberField = createNumberField(20);
        cardNumberField.setMaximumSize(new Dimension(520, 45));
        formPanel.add(cardNumberField);
        formPanel.add(Box.createVerticalStrut(15));
        JLabel cardHolderLabel = new JLabel("Kártyatulajdonos neve:");
        cardHolderLabel.setForeground(TEXT_COLOR);
        cardHolderLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JTextField cardHolderField = createStyledTextField("");
        formPanel.add(cardHolderLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(cardHolderField);
        formPanel.add(Box.createVerticalStrut(15));
        JLabel cvcLabel = new JLabel("CVC (3 számjegy):");
        cvcLabel.setForeground(TEXT_COLOR);
        cvcLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JTextField cvcField = createNumberField(3);
        cvcField.setMaximumSize(new Dimension(100, 45));
        JPanel cvcPanel = new JPanel();
        cvcPanel.setOpaque(false);
        cvcPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        cvcPanel.setMaximumSize(new Dimension(520, 50));
        cvcPanel.add(cvcField);
        formPanel.add(cvcLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(cvcPanel);
        formPanel.add(Box.createVerticalStrut(15));
        JLabel amountLabel = new JLabel("Befizetendő összeg ($):");
        amountLabel.setForeground(TEXT_COLOR);
        amountLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JTextField amountField = createNumberField(10);
        amountField.setMaximumSize(new Dimension(200, 45));
        JPanel amountPanel = new JPanel();
        amountPanel.setOpaque(false);
        amountPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        amountPanel.setMaximumSize(new Dimension(520, 50));
        amountPanel.add(amountField);
        formPanel.add(amountLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(amountPanel);
        formPanel.add(Box.createVerticalStrut(15));
        JLabel couponLabel = new JLabel("Kuponkód (opcionális):");
        couponLabel.setForeground(TEXT_COLOR);
        couponLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JTextField couponField = createStyledTextField("");
        JLabel couponInfoLabel = new JLabel("Elérhető kódok: GYPSY10, MARIJO50, LAKATOS_NARUTO250");
        couponInfoLabel.setForeground(new Color(150, 150, 150));
        couponInfoLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        formPanel.add(couponLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(couponField);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(couponInfoLabel);
        formPanel.add(Box.createVerticalStrut(25));
        JButton depositButton = createStyledButton("Befizetés", new Color(50, 150, 50));
        depositButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        depositButton.addActionListener(e -> processDeposit(cardNumberField, cardHolderField, cvcField, amountField, couponField));
        formPanel.add(depositButton);
        centerPanel.add(formPanel);
        depositPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(depositPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void processDeposit(JTextField cardNumberField, JTextField cardHolderField, JTextField cvcField, JTextField amountField, JTextField couponField) {
        try {
            if (cardNumberField.getText().length() != 20) {
                JOptionPane.showMessageDialog(this, "A kártyaszámnak pontosan 20 számjegyből kell állnia!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cardHolderField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kérlek add meg a kártyatulajdonos nevét!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cvcField.getText().length() != 3) {
                JOptionPane.showMessageDialog(this, "A CVC kódnak 3 számjegyből kell állnia!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int amount = Integer.parseInt(amountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Az összegnek pozitívnak kell lennie!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String couponCode = couponField.getText().trim().toUpperCase();
            int bonus = 0;
            if (!couponCode.isEmpty() && couponCodes.containsKey(couponCode)) {
                bonus = couponCodes.get(couponCode);
                amount += bonus;
            }
            currentUser.balance = (currentUser.balance + amount);
            balanceLabel.setText("Egyenleg: $" + currentUser.balance);
            String message = "Sikeres befizetés: $" + (amount - bonus);
            if (bonus > 0) {
                message += "\n🎉 Bónusz: $" + bonus + "\nÖsszesen: $" + amount;
            }
            JOptionPane.showMessageDialog(this, message, "Siker", JOptionPane.INFORMATION_MESSAGE);
            cardNumberField.setText("");
            cardHolderField.setText("");
            cvcField.setText("");
            amountField.setText("");
            couponField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Érvénytelen összeg!", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showWithdrawContent() {
        contentPanel.removeAll();
        JPanel withdrawPanel = new JPanel();
        withdrawPanel.setOpaque(false);
        withdrawPanel.setLayout(new BorderLayout());
        
        JButton closeButton = createCloseButton();
        closeButton.addActionListener(e -> showWelcomeContent());
        JPanel closePanel = new JPanel();
        closePanel.setOpaque(false);
        closePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        closePanel.add(closeButton);
        withdrawPanel.add(closePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());
        JPanel formPanel = new JPanel();
        formPanel.setBackground(PANEL_COLOR);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        formPanel.setPreferredSize(new Dimension(500, 400));
        JLabel titleLabel = new JLabel("💸 Kifizetés");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(GOLD_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(30));
        JLabel currentBalanceLabel = new JLabel("Jelenlegi egyenleg: $" + currentUser.balance);
        currentBalanceLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        currentBalanceLabel.setForeground(TEXT_COLOR);
        currentBalanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(currentBalanceLabel);
        formPanel.add(Box.createVerticalStrut(30));
        JLabel amountLabel = new JLabel("Kifizetendő összeg ($):");
        amountLabel.setForeground(TEXT_COLOR);
        amountLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JTextField amountField = createNumberField(10);
        amountField.setMaximumSize(new Dimension(400, 45));
        formPanel.add(amountLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(amountField);
        formPanel.add(Box.createVerticalStrut(30));
        JButton withdrawButton = createStyledButton("Kifizetés", new Color(150, 50, 50));
        withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        withdrawButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountField.getText());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Az összegnek pozitívnak kell lennie!", "Hiba", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount > currentUser.balance) {
                    JOptionPane.showMessageDialog(this, "Nincs elég egyenleged!", "Hiba", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                currentUser.balance =  (currentUser.balance - amount);
                balanceLabel.setText("Egyenleg: $" + currentUser.balance);
                currentBalanceLabel.setText("Jelenlegi egyenleg: $" + currentUser.balance);
                JOptionPane.showMessageDialog(this, "Sikeres kifizetés: $" + amount, "Siker", JOptionPane.INFORMATION_MESSAGE);
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Érvénytelen összeg!", "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        });
        formPanel.add(withdrawButton);
        centerPanel.add(formPanel);
        withdrawPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(withdrawPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    
    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(new LineBorder(FIELD_BORDER, 2, true), new EmptyBorder(10, 15, 10, 15)));
        field.setMaximumSize(new Dimension(520, 45));
        return field;
    }
    
    private JTextField createNumberField(int maxLength) {
        JTextField field = new JTextField(maxLength);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(new LineBorder(FIELD_BORDER, 2, true), new EmptyBorder(10, 15, 10, 15)));
        
        // Limit input to numbers only and max length
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || field.getText().length() >= maxLength) {
                    e.consume();
                }
            }
        });
        
        return field;
    }
    
    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = baseColor;
                if (getModel().isPressed()) {
                    color = color.darker();
                } else if (getModel().isRollover()) {
                    color = color.brighter();
                }
                g2d.setColor(color);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private JButton createCloseButton() {
        JButton closeButton = new JButton("✕") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = new Color(150, 50, 50);
                if (getModel().isPressed()) {
                    color = color.darker();
                } else if (getModel().isRollover()) {
                    color = color.brighter();
                }
                g2d.setColor(color);
                g2d.fillOval(0, 0, getWidth(), getHeight());
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        closeButton.setPreferredSize(new Dimension(40, 40));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return closeButton;
    }
    
    private JButton createGameBlock(String gameName) {
        JButton gameBlock = new JButton(gameName) {
            private float hoverScale = 1.0f;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                int w = getWidth();
                int h = getHeight();
                
                // Shadow effect
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fill(new RoundRectangle2D.Double(5, 5, w - 5, h - 5, 30, 30));
                
                // Main gradient background
                GradientPaint bgGradient;
                if (getModel().isPressed()) {
                    bgGradient = new GradientPaint(0, 0, BUTTON_PRESSED, 0, h, BUTTON_PRESSED.darker());
                } else if (getModel().isRollover()) {
                    bgGradient = new GradientPaint(0, 0, BUTTON_HOVER, 0, h, GOLD_COLOR);
                } else {
                    bgGradient = new GradientPaint(0, 0, BUTTON_COLOR, 0, h, GOLD_DARK);
                }
                g2d.setPaint(bgGradient);
                g2d.fill(new RoundRectangle2D.Double(0, 0, w - 5, h - 5, 30, 30));
                
                // Shine effect on top
                GradientPaint shine = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 80),
                    0, h / 3, new Color(255, 255, 255, 0)
                );
                g2d.setPaint(shine);
                g2d.fill(new RoundRectangle2D.Double(0, 0, w - 5, h / 3, 30, 30));
                
                // Border glow
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(255, 215, 0, 150));
                    g2d.setStroke(new BasicStroke(3));
                    g2d.draw(new RoundRectangle2D.Double(1, 1, w - 7, h - 7, 30, 30));
                }
                
                // Text with shadow
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (w - fm.stringWidth(getText())) / 2;
                int y = (h + fm.getAscent() - fm.getDescent()) / 2;
                
                // Text shadow
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(getText(), x + 2, y + 2);
                
                // Main text
                g2d.setColor(BACKGROUND_COLOR);
                g2d.drawString(getText(), x, y);
            }
        };
        
        gameBlock.setFont(new Font("SansSerif", Font.BOLD, 28));
        gameBlock.setFocusPainted(false);
        gameBlock.setBorderPainted(false);
        gameBlock.setContentAreaFilled(false);
        gameBlock.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gameBlock.addActionListener(e -> handleMenuAction(gameName));
        
        return gameBlock;
    }
    
    private void showBlackjackGame() {
        blackjackGameDialog.setup(this, currentUser, balanceLabel);
    }
    
    private void showRouletteGame() {
        new RouletteGame(this, currentUser, balanceLabel);
    }
    
    private void showCrashGame() {
        new CrashGame(this, currentUser, balanceLabel);
    }
    
    private void showLottoGame() {
        new LottoGame(this, currentUser, balanceLabel);
    }

}
