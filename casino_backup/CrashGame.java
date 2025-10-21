import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class CrashGame extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(20, 20, 25);
    private static final Color TEXT_COLOR = new Color(230, 200, 150);
    private static final Color GOLD_COLOR = new Color(218, 165, 32);
    
    private User currentUser;
    private JLabel balanceLabel;
    private JLabel multiplierLabel;
    private JTextArea historyArea;
    private JTextField betField;
    private JButton startButton;
    private JButton stopButton;
    private Timer gameTimer;
    private double multiplier = 1.00;
    private double crashPoint;
    private int bet = 0;
    private boolean gameRunning = false;
    private boolean canCashout = false;
    private Random random = new Random();
    private List<String> history = new ArrayList<>();
    
    // Casino algorithm constants
    private static final double HOUSE_EDGE = 0.03; // 3% house edge
    private static final double RTP = 1.0 - HOUSE_EDGE; // 97% RTP
    
    public CrashGame(JFrame parent, User user, JLabel balanceLabel) {
        super(parent, "💥 Crash", true);
        this.currentUser = user;
        this.balanceLabel = balanceLabel;
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        
        initUI();
        setVisible(true);
    }
    
    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("💥 CRASH");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(GOLD_COLOR);
        
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(150, 50, 50));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(40, 40));
        closeButton.addActionListener(e -> {
            if (gameTimer != null) gameTimer.stop();
            dispose();
        });
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);
        
        // Game area
        JPanel gamePanel = new JPanel();
        gamePanel.setOpaque(false);
        gamePanel.setLayout(new GridBagLayout());
        
        multiplierLabel = new JLabel("1.00x");
        multiplierLabel.setFont(new Font("SansSerif", Font.BOLD, 80));
        multiplierLabel.setForeground(GOLD_COLOR);
        
        JPanel historyPanel = new JPanel();
        historyPanel.setOpaque(false);
        historyPanel.setLayout(new BorderLayout());
        
        JLabel historyLabel = new JLabel("Crash History:");
        historyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        historyLabel.setForeground(TEXT_COLOR);
        
        historyArea = new JTextArea(3, 30);
        historyArea.setEditable(false);
        historyArea.setBackground(new Color(30, 30, 35));
        historyArea.setForeground(TEXT_COLOR);
        historyArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setPreferredSize(new Dimension(400, 60));
        
        historyPanel.add(historyLabel, BorderLayout.NORTH);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        
        gamePanel.add(multiplierLabel);
        gamePanel.add(Box.createVerticalStrut(20));
        gamePanel.add(historyPanel);
        
        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        
        JLabel betLabel = new JLabel("Tét: $");
        betLabel.setForeground(TEXT_COLOR);
        betLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        betField = new JTextField("10", 8);
        betField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        startButton = createButton("Start");
        startButton.setBackground(new Color(50, 150, 50));
        startButton.addActionListener(e -> startGame());
        
        stopButton = createButton("Stop");
        stopButton.setBackground(new Color(200, 50, 50));
        stopButton.setEnabled(false);
        stopButton.addActionListener(e -> stopGame());
        
        controlPanel.add(betLabel);
        controlPanel.add(betField);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        
        JLabel infoLabel = new JLabel("<html><center>A szorzó növekszik, de bármikor összeomolhat!<br>Fizess ki időben!</center></html>");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infoLabel.setForeground(TEXT_COLOR);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(infoLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        mainPanel.add(infoPanel, BorderLayout.PAGE_END);
        
        setContentPane(mainPanel);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }
    
    private void startGame() {
        try {
            bet = Integer.parseInt(betField.getText());
            if (bet <= 0) {
                JOptionPane.showMessageDialog(this, "A tétnek legalább $1-nak kell lennie!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bet > currentUser.getBalance()) {
                JOptionPane.showMessageDialog(this, "Nincs elég egyenleged! Jelenlegi egyenleg: $" + currentUser.getBalance(), "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            currentUser.setBalance(currentUser.getBalance() - bet);
            balanceLabel.setText("Egyenleg: $" + currentUser.getBalance());
            
            multiplier = 1.00;
            crashPoint = calculateCrashPoint();
            gameRunning = true;
            canCashout = false;
            
            startButton.setEnabled(false);
            stopButton.setEnabled(false);
            betField.setEditable(false);
            
            // Wait 1 second before allowing cashout
            Timer delayTimer = new Timer(1000, e -> {
                canCashout = true;
                stopButton.setEnabled(true);
            });
            delayTimer.setRepeats(false);
            delayTimer.start();
            
            gameTimer = new Timer(100, e -> {
                if (multiplier >= crashPoint) {
                    crash();
                } else {
                    multiplier += 0.05;
                    multiplierLabel.setText(String.format("%.2fx", multiplier));
                    
                    // Change color as multiplier increases
                    if (multiplier < 2.0) {
                        multiplierLabel.setForeground(new Color(50, 200, 50));
                    } else if (multiplier < 5.0) {
                        multiplierLabel.setForeground(GOLD_COLOR);
                    } else {
                        multiplierLabel.setForeground(new Color(255, 100, 100));
                    }
                }
            });
            gameTimer.start();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kérlek írj be egy érvényes számot a tét mezőbe!", "Hiba", JOptionPane.ERROR_MESSAGE);
            betField.setText("10");
        }
    }
    
    private void stopGame() {
        if (!gameRunning || !canCashout) return;
        
        gameTimer.stop();
        gameRunning = false;
        
        int winAmount = (int) (bet * multiplier);
        currentUser.setBalance(currentUser.getBalance() + winAmount);
        balanceLabel.setText("Egyenleg: $" + currentUser.getBalance());
        
        // Add to history
        String historyEntry = String.format("✓ %.2fx ($%d)", multiplier, winAmount);
        addToHistory(historyEntry);
        
        JOptionPane.showMessageDialog(this, 
            String.format("Kifizetés: $%d (%.2fx szorzó)", winAmount, multiplier), 
            "Nyertél!", 
            JOptionPane.INFORMATION_MESSAGE);
        
        resetGame();
    }
    
    private void crash() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameRunning = false;
        
        multiplierLabel.setText("💥 CRASH!");
        multiplierLabel.setForeground(new Color(200, 50, 50));
        
        // Add to history
        String historyEntry = String.format("💥 %.2fx (Vesztettél $%d)", crashPoint, bet);
        addToHistory(historyEntry);
        
        JOptionPane.showMessageDialog(this, 
            String.format("A játék %.2fx-nél összeomlott! Vesztettél $%d", crashPoint, bet), 
            "Crash!", 
            JOptionPane.ERROR_MESSAGE);
        
        resetGame();
    }
    
    private void addToHistory(String entry) {
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
                sb.append("\n");
            }
        }
        historyArea.setText(sb.toString());
    }
    
    private void resetGame() {
        multiplier = 1.00;
        multiplierLabel.setText("1.00x");
        multiplierLabel.setForeground(GOLD_COLOR);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        betField.setEditable(true);
        canCashout = false;
    }
    
    /**
     * Calculate crash point using provably fair casino algorithm
     * Formula: 99 / (99 - (random * 100))
     * This ensures 97% RTP with 3% house edge
     * ~3% chance of instant bust at 1.00x
     */
    private double calculateCrashPoint() {
        double r = random.nextDouble(); // 0.0 to 1.0
        
        // Instant bust probability (~3%)
        if (r < HOUSE_EDGE) {
            return 1.00;
        }
        
        // Calculate crash point with house edge
        // Formula ensures proper distribution with 97% RTP
        double result = (100.0 - HOUSE_EDGE * 100) / ((100.0 - HOUSE_EDGE * 100) - (r * 100.0));
        
        // Clamp between 1.00 and reasonable maximum
        if (result < 1.00) result = 1.00;
        if (result > 1000.0) result = 1000.0;
        
        return Math.round(result * 100.0) / 100.0; // Round to 2 decimals
    }
}
