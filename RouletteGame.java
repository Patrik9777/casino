import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;

public class RouletteGame extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(20, 20, 25);
    private static final Color PANEL_COLOR = new Color(30, 30, 35);
    private static final Color TEXT_COLOR = new Color(230, 200, 150);
    private static final Color GOLD_COLOR = new Color(218, 165, 32);
    private static final Color RED_COLOR = new Color(200, 50, 50);
    private static final Color BLACK_COLOR = new Color(50, 50, 50);
    private static final Color GREEN_COLOR = new Color(50, 150, 50);
    
    private User currentUser;
    private JLabel balanceLabel;
    private JLabel resultLabel;
    private JTextField betField;
    private JButton spinButton;
    private JPanel numberPanel;
    private RouletteWheel wheelPanel;
    private int selectedNumber = -1;
    private String selectedColor = "";
    private Random random = new Random();
    
    // Proper European roulette red numbers
    private int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    private int[] rouletteOrder = {0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26};
    
    public RouletteGame(JFrame parent, User user, JLabel balanceLabel) {
        super(parent, "🎲 Rulett", true);
        this.currentUser = user;
        this.balanceLabel = balanceLabel;
        
        setSize(900, 700);
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
        
        JLabel titleLabel = new JLabel("🎲 RULETT");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(GOLD_COLOR);
        
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(150, 50, 50));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(40, 40));
        closeButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);
        
        // Game area
        JPanel gamePanel = new JPanel();
        gamePanel.setOpaque(false);
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        resultLabel = new JLabel("Válassz számot vagy színt!");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        resultLabel.setForeground(GOLD_COLOR);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Roulette wheel
        wheelPanel = new RouletteWheel();
        wheelPanel.setPreferredSize(new Dimension(250, 250));
        wheelPanel.setMaximumSize(new Dimension(250, 250));
        wheelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Number grid
        numberPanel = new JPanel();
        numberPanel.setOpaque(false);
        numberPanel.setLayout(new GridLayout(4, 10, 5, 5));
        numberPanel.setMaximumSize(new Dimension(800, 300));
        
        // Add 0 (green)
        JButton zeroButton = createNumberButton(0, GREEN_COLOR);
        numberPanel.add(zeroButton);
        
        // Add numbers 1-36
        for (int i = 1; i <= 36; i++) {
            Color color = isRed(i) ? RED_COLOR : BLACK_COLOR;
            JButton numButton = createNumberButton(i, color);
            numberPanel.add(numButton);
        }
        
        // Color buttons
        JPanel colorPanel = new JPanel();
        colorPanel.setOpaque(false);
        colorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton redButton = createColorButton("PIROS", RED_COLOR);
        JButton blackButton = createColorButton("FEKETE", BLACK_COLOR);
        
        colorPanel.add(redButton);
        colorPanel.add(blackButton);
        
        gamePanel.add(resultLabel);
        gamePanel.add(Box.createVerticalStrut(15));
        gamePanel.add(wheelPanel);
        gamePanel.add(Box.createVerticalStrut(15));
        gamePanel.add(numberPanel);
        gamePanel.add(Box.createVerticalStrut(15));
        gamePanel.add(colorPanel);
        
        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JLabel betLabel = new JLabel("Tét: $");
        betLabel.setForeground(TEXT_COLOR);
        betLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        betField = new JTextField("10", 8);
        betField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        spinButton = createButton("Pörgess!");
        spinButton.addActionListener(e -> spin());
        
        JButton clearButton = createButton("Törlés");
        clearButton.addActionListener(e -> clearSelection());
        
        controlPanel.add(betLabel);
        controlPanel.add(betField);
        controlPanel.add(spinButton);
        controlPanel.add(clearButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JButton createNumberButton(int number, Color color) {
        JButton button = new JButton(String.valueOf(number));
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> {
            selectedNumber = number;
            selectedColor = "";
            resultLabel.setText("Választott szám: " + number);
        });
        return button;
    }
    
    private JButton createColorButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 50));
        button.addActionListener(e -> {
            selectedNumber = -1;
            selectedColor = text;
            resultLabel.setText("Választott szín: " + text);
        });
        return button;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(GOLD_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }
    
    private void clearSelection() {
        selectedNumber = -1;
        selectedColor = "";
        resultLabel.setText("Válassz számot vagy színt!");
    }
    
    private void spin() {
        if (selectedNumber == -1 && selectedColor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Válassz számot vagy színt!", "Hiba", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int bet = Integer.parseInt(betField.getText());
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
            
            spinButton.setEnabled(false);
            
            final int result = random.nextInt(37); // 0-36
            
            // Animate wheel
            wheelPanel.spin(result, () -> {
                String resultColor = result == 0 ? "ZÖLD" : (isRed(result) ? "PIROS" : "FEKETE");
                resultLabel.setText("Eredmény: " + result + " (" + resultColor + ")");
                
                boolean won = false;
                int winAmount = 0;
            
            if (selectedNumber != -1 && selectedNumber == result) {
                won = true;
                winAmount = bet * 36; // 35:1 payout + original bet
            } else if (!selectedColor.isEmpty() && selectedColor.equals(resultColor)) {
                won = true;
                winAmount = bet * 2; // 1:1 payout + original bet
            }
            
                if (won) {
                    currentUser.setBalance(currentUser.getBalance() + winAmount);
                    JOptionPane.showMessageDialog(this, "Nyertél $" + winAmount + "!", "Gratulálunk!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Vesztettél!", "Sajnos", JOptionPane.INFORMATION_MESSAGE);
                }
                
                balanceLabel.setText("Egyenleg: $" + currentUser.getBalance());
                clearSelection();
                spinButton.setEnabled(true);
            });
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kérlek írj be egy érvényes számot a tét mezőbe!", "Hiba", JOptionPane.ERROR_MESSAGE);
            betField.setText("10");
        }
    }
    
    private boolean isRed(int number) {
        for (int red : redNumbers) {
            if (red == number) return true;
        }
        return false;
    }
    
    // Inner class for animated roulette wheel
    private class RouletteWheel extends JPanel {
        private double rotation = 0;
        private Timer spinTimer;
        private int targetNumber;
        private Runnable callback;
        
        public RouletteWheel() {
            setOpaque(false);
        }
        
        public void spin(int targetNum, Runnable onComplete) {
            this.targetNumber = targetNum;
            this.callback = onComplete;
            
            if (spinTimer != null) spinTimer.stop();
            
            // Find index of target number in roulette order
            int targetIndex = 0;
            for (int i = 0; i < rouletteOrder.length; i++) {
                if (rouletteOrder[i] == targetNum) {
                    targetIndex = i;
                    break;
                }
            }
            
            double targetAngle = (targetIndex * 360.0 / 37.0);
            double spins = 5 + random.nextDouble() * 3; // 5-8 full rotations
            final double totalRotation = spins * 360 + targetAngle;
            final double[] speed = {20.0}; // Initial speed
            
            spinTimer = new Timer(30, e -> {
                rotation += speed[0];
                speed[0] *= 0.98; // Deceleration
                
                if (rotation >= totalRotation) {
                    rotation = targetAngle;
                    spinTimer.stop();
                    if (callback != null) {
                        callback.run();
                    }
                }
                repaint();
            });
            spinTimer.start();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = Math.min(getWidth(), getHeight()) / 2 - 10;
            
            // Draw wheel segments in proper roulette order
            double angleStep = 360.0 / 37.0;
            for (int i = 0; i < 37; i++) {
                int number = rouletteOrder[i];
                double startAngle = rotation + i * angleStep;
                
                Color segmentColor;
                if (number == 0) {
                    segmentColor = GREEN_COLOR;
                } else {
                    segmentColor = isRed(number) ? RED_COLOR : BLACK_COLOR;
                }
                
                g2d.setColor(segmentColor);
                g2d.fill(new Arc2D.Double(
                    centerX - radius, centerY - radius,
                    radius * 2, radius * 2,
                    startAngle, angleStep, Arc2D.PIE
                ));
                
                // Draw number
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 10));
                double midAngle = Math.toRadians(startAngle + angleStep / 2);
                int textX = (int) (centerX + Math.cos(midAngle) * radius * 0.7);
                int textY = (int) (centerY - Math.sin(midAngle) * radius * 0.7);
                String numStr = String.valueOf(number);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(numStr, textX - fm.stringWidth(numStr) / 2, textY + fm.getAscent() / 2);
            }
            
            // Draw center circle
            g2d.setColor(GOLD_COLOR);
            int centerRadius = 20;
            g2d.fillOval(centerX - centerRadius, centerY - centerRadius, centerRadius * 2, centerRadius * 2);
            
            // Draw pointer at top
            g2d.setColor(Color.WHITE);
            int[] xPoints = {centerX, centerX - 10, centerX + 10};
            int[] yPoints = {centerY - radius + 5, centerY - radius + 20, centerY - radius + 20};
            g2d.fillPolygon(xPoints, yPoints, 3);
        }
    }
}
