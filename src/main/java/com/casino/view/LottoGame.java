package com.casino.view;

import com.casino.models.User;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LottoGame extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(20, 20, 25);
    private static final Color PANEL_COLOR = new Color(30, 30, 35);
    private static final Color TEXT_COLOR = new Color(230, 200, 150);
    private static final Color GOLD_COLOR = new Color(218, 165, 32);
    
    private User currentUser;
    private JLabel balanceLabel;
    private JLabel resultLabel;
    private JTextField betField;
    private JButton playButton;
    private Set<Integer> selectedNumbers = new HashSet<>();
    private JButton[] numberButtons = new JButton[45];
    private Random random = new Random();
    
    public LottoGame(JFrame parent, User user, JLabel balanceLabel) {
        super(parent, "🎟️ Lotto", true);
        this.currentUser = user;
        this.balanceLabel = balanceLabel;
        
        setSize(700, 700);
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
        
        JLabel titleLabel = new JLabel("🎟️ LOTTO (5/45)");
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
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel infoLabel = new JLabel("Válassz 5 számot 1 és 45 között!");
        infoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        infoLabel.setForeground(TEXT_COLOR);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        resultLabel.setForeground(GOLD_COLOR);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(infoLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(resultLabel);
        
        // Number grid
        JPanel numberPanel = new JPanel();
        numberPanel.setOpaque(false);
        numberPanel.setLayout(new GridLayout(9, 5, 5, 5));
        numberPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        
        for (int i = 1; i <= 45; i++) {
            final int number = i;
            JButton button = new JButton(String.valueOf(i));
            button.setFont(new Font("SansSerif", Font.BOLD, 16));
            button.setBackground(PANEL_COLOR);
            button.setForeground(TEXT_COLOR);
            button.setFocusPainted(false);
            button.setBorderPainted(true);
            button.setBorder(BorderFactory.createLineBorder(GOLD_COLOR, 1));
            
            button.addActionListener(e -> toggleNumber(number, button));
            numberButtons[i - 1] = button;
            numberPanel.add(button);
        }
        
        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        
        JLabel betLabel = new JLabel("Tét: $");
        betLabel.setForeground(TEXT_COLOR);
        betLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        betField = new JTextField("10", 8);
        betField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        playButton = createButton("Sorsolás");
        playButton.addActionListener(e -> play());
        
        JButton randomButton = createButton("Véletlen");
        randomButton.addActionListener(e -> selectRandom());
        
        JButton clearButton = createButton("Törlés");
        clearButton.addActionListener(e -> clearSelection());
        
        JButton closeModalButton = createButton("Bezárás");
        closeModalButton.addActionListener(e -> dispose());
        
        controlPanel.add(betLabel);
        controlPanel.add(betField);
        controlPanel.add(playButton);
        controlPanel.add(randomButton);
        controlPanel.add(clearButton);
        controlPanel.add(closeModalButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.PAGE_START);
        mainPanel.add(numberPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(GOLD_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 40));
        return button;
    }
    
    private void toggleNumber(int number, JButton button) {
        if (selectedNumbers.contains(number)) {
            selectedNumbers.remove(number);
            button.setBackground(PANEL_COLOR);
            button.setForeground(TEXT_COLOR);
        } else {
            if (selectedNumbers.size() < 5) {
                selectedNumbers.add(number);
                button.setBackground(GOLD_COLOR);
                button.setForeground(Color.BLACK);
            } else {
                JOptionPane.showMessageDialog(this, "Már kiválasztottál 5 számot!", "Figyelem", JOptionPane.WARNING_MESSAGE);
            }
        }
        updateResultLabel();
    }
    
    private void selectRandom() {
        clearSelection();
        while (selectedNumbers.size() < 5) {
            int num = random.nextInt(45) + 1;
            if (!selectedNumbers.contains(num)) {
                selectedNumbers.add(num);
                numberButtons[num - 1].setBackground(GOLD_COLOR);
                numberButtons[num - 1].setForeground(Color.BLACK);
            }
        }
        updateResultLabel();
    }
    
    private void clearSelection() {
        selectedNumbers.clear();
        for (JButton button : numberButtons) {
            button.setBackground(PANEL_COLOR);
            button.setForeground(TEXT_COLOR);
        }
        updateResultLabel();
    }
    
    private void updateResultLabel() {
        if (selectedNumbers.isEmpty()) {
            resultLabel.setText(" ");
        } else {
            List<Integer> sorted = new ArrayList<>(selectedNumbers);
            Collections.sort(sorted);
            resultLabel.setText("Választott számok: " + sorted.toString());
        }
    }
    
    private void play() {
        if (selectedNumbers.size() != 5) {
            JOptionPane.showMessageDialog(this, "Válassz pontosan 5 számot!", "Hiba", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int bet = Integer.parseInt(betField.getText());
            if (bet <= 0) {
                JOptionPane.showMessageDialog(this, "A tétnek legalább $1-nak kell lennie!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bet > currentUser.balance) {
                JOptionPane.showMessageDialog(this, "Nincs elég egyenleged! Jelenlegi egyenleg: $" + currentUser.balance, "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            currentUser.balance =(currentUser.balance - bet);
            
            // Generate winning numbers
            Set<Integer> winningNumbers = new HashSet<>();
            while (winningNumbers.size() < 5) {
                winningNumbers.add(random.nextInt(45) + 1);
            }
            
            List<Integer> winningList = new ArrayList<>(winningNumbers);
            Collections.sort(winningList);
            
            // Count matches
            Set<Integer> matches = new HashSet<>(selectedNumbers);
            matches.retainAll(winningNumbers);
            int matchCount = matches.size();
            
            // Calculate winnings
            int winAmount = 0;
            String message = "Nyerő számok: " + winningList.toString() + "\n\n";
            
            switch (matchCount) {
                case 5:
                    winAmount = bet * 1000; // Jackpot!
                    message += "🎉 JACKPOT! Mind az 5 szám talált!\nNyeremény: $" + winAmount;
                    break;
                case 4:
                    winAmount = bet * 50;
                    message += "🎊 4 találat!\nNyeremény: $" + winAmount;
                    break;
                case 3:
                    winAmount = bet * 10;
                    message += "✨ 3 találat!\nNyeremény: $" + winAmount;
                    break;
                case 2:
                    winAmount = bet * 2;
                    message += "⭐ 2 találat!\nNyeremény: $" + winAmount;
                    break;
                default:
                    message += "Sajnos nem nyertél ezúttal.\nTalálatok: " + matchCount;
                    break;
            }
            
            if (winAmount > 0) {
                currentUser.balance = (currentUser.balance + winAmount);
            }
            
            balanceLabel.setText("Egyenleg: $" + currentUser.balance);
            
            JOptionPane.showMessageDialog(this, message, "Eredmény", JOptionPane.INFORMATION_MESSAGE);
            clearSelection();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kérlek írj be egy érvényes számot a tét mezőbe!", "Hiba", JOptionPane.ERROR_MESSAGE);
            betField.setText("10");
        }
    }
}
