import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class CrashGame extends JDialog {
    // Colors
    private static final Color BG_DARK = new Color(15, 15, 20);
    private static final Color BG_PANEL = new Color(25, 25, 30);
    private static final Color TEXT_LIGHT = new Color(220, 220, 230);
    private static final Color GOLD = new Color(255, 215, 0);
    private static final Color GREEN = new Color(34, 197, 94);
    private static final Color RED = new Color(239, 68, 68);
    private static final Color ORANGE = new Color(251, 146, 60);
    
    // Components
    private User currentUser;
    private JLabel balanceLabel;
    private JLabel multiplierLabel;
    private JLabel statusLabel;
    private JTextField betField;
    private JButton betButton;
    private JButton cashoutButton;
    private JTextArea historyArea;
    
    // Game state
    private Timer gameTimer;
    private double currentMultiplier;
    private double crashPoint;
    private int currentBet;
    private boolean isGameRunning;
    private boolean hasBet;
    private Random random;
    private List<String> history;
    
    // Constants
    private static final double HOUSE_EDGE = 0.03;
    private static final int GAME_SPEED = 50;
    private static final double MULTIPLIER_INCREMENT = 0.01;
    
    public CrashGame(JFrame parent, User user, JLabel balanceLabel) {
        super(parent, "💥 Crash Game", true);
        this.currentUser = user;
        this.balanceLabel = balanceLabel;
        this.random = new Random();
        this.history = new ArrayList<>();
        this.isGameRunning = false;
        this.hasBet = false;
        this.currentMultiplier = 1.00;
        
        setSize(700, 600);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        
        initUI();
        startNewRound();
        setVisible(true);
    }
    
    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);
        mainPanel.setBorder(BorderFactory.createLineBorder(GOLD, 2));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_PANEL);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("💥 CRASH GAME");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(GOLD);
        
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(RED);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(45, 45));
        closeButton.addActionListener(e -> {
            if (gameTimer != null) gameTimer.stop();
            dispose();
        });
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);
        
        // Center - Multiplier Display
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BG_DARK);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        JPanel multiplierPanel = new JPanel();
        multiplierPanel.setLayout(new BoxLayout(multiplierPanel, BoxLayout.Y_AXIS));
        multiplierPanel.setBackground(BG_PANEL);
        multiplierPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD, 3),
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        
        multiplierLabel = new JLabel("1.00x");
        multiplierLabel.setFont(new Font("SansSerif", Font.BOLD, 72));
        multiplierLabel.setForeground(GREEN);
        multiplierLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        statusLabel = new JLabel("Várakozás...");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        statusLabel.setForeground(TEXT_LIGHT);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        multiplierPanel.add(multiplierLabel);
        multiplierPanel.add(Box.createVerticalStrut(10));
        multiplierPanel.add(statusLabel);
        
        centerPanel.add(multiplierPanel);
        
        // Bottom - Controls and History
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setBackground(BG_DARK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBackground(BG_PANEL);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel betLabel = new JLabel("Tét:");
        betLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        betLabel.setForeground(TEXT_LIGHT);
        
        betField = new JTextField("10", 10);
        betField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        betField.setBackground(BG_DARK);
        betField.setForeground(TEXT_LIGHT);
        betField.setCaretColor(TEXT_LIGHT);
        betField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        betButton = new JButton("TÉTET HELYEZ");
        betButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        betButton.setBackground(GREEN);
        betButton.setForeground(Color.WHITE);
        betButton.setFocusPainted(false);
        betButton.setBorderPainted(false);
        betButton.setPreferredSize(new Dimension(150, 40));
        betButton.addActionListener(e -> placeBet());
        
        cashoutButton = new JButton("KIFIZETÉS");
        cashoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        cashoutButton.setBackground(ORANGE);
        cashoutButton.setForeground(Color.WHITE);
        cashoutButton.setFocusPainted(false);
        cashoutButton.setBorderPainted(false);
        cashoutButton.setPreferredSize(new Dimension(150, 40));
        cashoutButton.setEnabled(false);
        cashoutButton.addActionListener(e -> cashout());
        
        controlPanel.add(betLabel);
        controlPanel.add(betField);
        controlPanel.add(betButton);
        controlPanel.add(cashoutButton);
        
        // History Panel
        JPanel historyPanelContainer = new JPanel(new BorderLayout());
        historyPanelContainer.setBackground(BG_PANEL);
        historyPanelContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel historyLabel = new JLabel("📊 Előzmények:");
        historyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        historyLabel.setForeground(GOLD);
        
        historyArea = new JTextArea(3, 50);
        historyArea.setEditable(false);
        historyArea.setBackground(BG_DARK);
        historyArea.setForeground(TEXT_LIGHT);
        historyArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        historyArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(null);
        
        historyPanelContainer.add(historyLabel, BorderLayout.NORTH);
        historyPanelContainer.add(scrollPane, BorderLayout.CENTER);
        
        bottomPanel.add(controlPanel, BorderLayout.NORTH);
        bottomPanel.add(historyPanelContainer, BorderLayout.CENTER);
        
        // Add all panels
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void startNewRound() {
        currentMultiplier = 1.00;
        crashPoint = calculateCrashPoint();
        isGameRunning = false;
        
        multiplierLabel.setText("1.00x");
        multiplierLabel.setForeground(GREEN);
        statusLabel.setText("Tétet helyezz!");
        
        betButton.setEnabled(true);
        cashoutButton.setEnabled(false);
        betField.setEditable(true);
        
        // Start countdown
        Timer countdownTimer = new Timer(1000, null);
        final int[] countdown = {3};
        
        countdownTimer.addActionListener(e -> {
            if (countdown[0] > 0) {
                statusLabel.setText("Indulás " + countdown[0] + " másodperc múlva...");
                countdown[0]--;
            } else {
                countdownTimer.stop();
                startGame();
            }
        });
        countdownTimer.start();
    }
    
    private void placeBet() {
        try {
            int bet = Integer.parseInt(betField.getText().trim());
            
            if (bet <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "A tétnek legalább $1-nak kell lennie!", 
                    "Érvénytelen tét", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (bet > currentUser.getBalance()) {
                JOptionPane.showMessageDialog(this, 
                    "Nincs elég egyenleged!\nJelenlegi egyenleg: $" + currentUser.getBalance(), 
                    "Nincs elég pénz", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            currentBet = bet;
            currentUser.setBalance(currentUser.getBalance() - bet);
            balanceLabel.setText("Egyenleg: $" + currentUser.getBalance());
            
            hasBet = true;
            betButton.setEnabled(false);
            betField.setEditable(false);
            statusLabel.setText("Tét elhelyezve: $" + bet);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Kérlek írj be egy érvényes számot!", 
                "Hiba", 
                JOptionPane.ERROR_MESSAGE);
            betField.setText("10");
        }
    }
    
    private void startGame() {
        isGameRunning = true;
        statusLabel.setText("Játék folyamatban...");
        
        if (hasBet) {
            cashoutButton.setEnabled(true);
        }
        
        gameTimer = new Timer(GAME_SPEED, e -> {
            currentMultiplier += MULTIPLIER_INCREMENT;
            updateMultiplierDisplay();
            
            if (currentMultiplier >= crashPoint) {
                crash();
            }
        });
        gameTimer.start();
    }
    
    private void updateMultiplierDisplay() {
        multiplierLabel.setText(String.format("%.2fx", currentMultiplier));
        
        if (currentMultiplier < 2.0) {
            multiplierLabel.setForeground(GREEN);
        } else if (currentMultiplier < 5.0) {
            multiplierLabel.setForeground(GOLD);
        } else {
            multiplierLabel.setForeground(ORANGE);
        }
    }
    
    private void cashout() {
        if (!hasBet || !isGameRunning) return;
        
        gameTimer.stop();
        
        int winAmount = (int) (currentBet * currentMultiplier);
        currentUser.setBalance(currentUser.getBalance() + winAmount);
        balanceLabel.setText("Egyenleg: $" + currentUser.getBalance());
        
        int profit = winAmount - currentBet;
        String result = String.format("✓ %.2fx → +$%d", currentMultiplier, profit);
        addToHistory(result, true);
        
        statusLabel.setText(String.format("Nyertél $%d! (%.2fx)", profit, currentMultiplier));
        multiplierLabel.setForeground(GREEN);
        
        JOptionPane.showMessageDialog(this, 
            String.format("Gratulálunk!\n\nKifizetés: $%d\nSzorzó: %.2fx\nNyeremény: $%d", 
                winAmount, currentMultiplier, profit), 
            "Nyertél!", 
            JOptionPane.INFORMATION_MESSAGE);
        
        hasBet = false;
        cashoutButton.setEnabled(false);
        
        Timer delayTimer = new Timer(2000, e -> {
            ((Timer)e.getSource()).stop();
            startNewRound();
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }
    
    private void crash() {
        gameTimer.stop();
        isGameRunning = false;
        
        multiplierLabel.setText("💥 CRASH!");
        multiplierLabel.setForeground(RED);
        statusLabel.setText(String.format("Összeomlott %.2fx-nél!", crashPoint));
        
        String result = String.format("💥 %.2fx", crashPoint);
        addToHistory(result, false);
        
        if (hasBet) {
            JOptionPane.showMessageDialog(this, 
                String.format("A játék %.2fx-nél összeomlott!\nVesztettél $%d", 
                    crashPoint, currentBet), 
                "Crash!", 
                JOptionPane.ERROR_MESSAGE);
            hasBet = false;
        }
        
        cashoutButton.setEnabled(false);
        
        Timer delayTimer = new Timer(3000, e -> {
            ((Timer)e.getSource()).stop();
            startNewRound();
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }
    
    private void addToHistory(String entry, boolean isWin) {
        history.add(0, entry);
        if (history.size() > 10) {
            history.remove(history.size() - 1);
        }
        updateHistoryDisplay();
    }
    
    private void updateHistoryDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(5, history.size()); i++) {
            sb.append(history.get(i));
            if (i < Math.min(5, history.size()) - 1) {
                sb.append("  |  ");
            }
        }
        historyArea.setText(sb.toString());
    }
    
    private double calculateCrashPoint() {
        double r = random.nextDouble();
        
        // 3% instant crash
        if (r < HOUSE_EDGE) {
            return 1.00;
        }
        
        // Provably fair algorithm
        double result = (100.0 - HOUSE_EDGE * 100) / ((100.0 - HOUSE_EDGE * 100) - (r * 100.0));
        
        if (result < 1.00) result = 1.00;
        if (result > 100.0) result = 100.0;
        
        return Math.round(result * 100.0) / 100.0;
    }
}
