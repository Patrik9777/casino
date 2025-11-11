package com.casino.view;

import com.casino.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlackjackGame extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(20, 40, 20);
    private static final Color PANEL_COLOR = new Color(30, 60, 30);
    private static final Color TEXT_COLOR = new Color(230, 200, 150);
    private static final Color GOLD_COLOR = new Color(218, 165, 32);
    
    private User currentUser;
    private JLabel balanceLabel;
    private JLabel playerScoreLabel;
    private JLabel dealerScoreLabel;
    private JPanel playerCardsPanel;
    private JPanel dealerCardsPanel;
    private JButton hitButton;
    private JButton standButton;
    private JButton newGameButton;
    private JTextField betField;
    
    private List<Integer> playerCards = new ArrayList<>();
    private List<Integer> dealerCards = new ArrayList<>();
    private int bet = 0;
    private Random random = new Random();
    
    public BlackjackGame(JFrame parent, User user, JLabel balanceLabel) {
        super(parent, "🎴 Blackjack", true);
        this.currentUser = user;
        this.balanceLabel = balanceLabel;
        
        setSize(800, 600);
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
        
        JLabel titleLabel = new JLabel("🎴 BLACKJACK");
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
        
        // Dealer area
        JLabel dealerLabel = new JLabel("Osztó");
        dealerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        dealerLabel.setForeground(TEXT_COLOR);
        dealerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        dealerScoreLabel = new JLabel("Pontszám: 0");
        dealerScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        dealerScoreLabel.setForeground(TEXT_COLOR);
        dealerScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        dealerCardsPanel = new JPanel();
        dealerCardsPanel.setOpaque(false);
        dealerCardsPanel.setPreferredSize(new Dimension(700, 120));
        dealerCardsPanel.setMaximumSize(new Dimension(700, 120));
        
        // Player area
        JLabel playerLabel = new JLabel("Te");
        playerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        playerLabel.setForeground(TEXT_COLOR);
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playerScoreLabel = new JLabel("Pontszám: 0");
        playerScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        playerScoreLabel.setForeground(TEXT_COLOR);
        playerScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playerCardsPanel = new JPanel();
        playerCardsPanel.setOpaque(false);
        playerCardsPanel.setPreferredSize(new Dimension(700, 120));
        playerCardsPanel.setMaximumSize(new Dimension(700, 120));
        
        gamePanel.add(dealerLabel);
        gamePanel.add(Box.createVerticalStrut(5));
        gamePanel.add(dealerScoreLabel);
        gamePanel.add(Box.createVerticalStrut(10));
        gamePanel.add(dealerCardsPanel);
        gamePanel.add(Box.createVerticalStrut(40));
        gamePanel.add(playerLabel);
        gamePanel.add(Box.createVerticalStrut(5));
        gamePanel.add(playerScoreLabel);
        gamePanel.add(Box.createVerticalStrut(10));
        gamePanel.add(playerCardsPanel);
        
        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JLabel betLabel = new JLabel("Tét: $");
        betLabel.setForeground(TEXT_COLOR);
        betLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        betField = new JTextField("10", 8);
        betField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        newGameButton = createButton("Új játék");
        newGameButton.addActionListener(e -> startNewGame());
        
        hitButton = createButton("Lapot kérek");
        hitButton.addActionListener(e -> hit());
        hitButton.setEnabled(false);
        
        standButton = createButton("Megállok");
        standButton.addActionListener(e -> stand());
        standButton.setEnabled(false);
        
        controlPanel.add(betLabel);
        controlPanel.add(betField);
        controlPanel.add(newGameButton);
        controlPanel.add(hitButton);
        controlPanel.add(standButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
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
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }
    
    private void startNewGame() {
        try {
            bet = Integer.parseInt(betField.getText());
            if (bet <= 0) {
                JOptionPane.showMessageDialog(this, "A tétnek legalább $1-nak kell lennie!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bet > currentUser.balance) {
                JOptionPane.showMessageDialog(this, "Nincs elég egyenleged! Jelenlegi egyenleg: $" + currentUser.balance, "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Levonás a tétből
            currentUser.balance = (currentUser.balance - bet);
            balanceLabel.setText("Egyenleg: $" + currentUser.balance);
            
            playerCards.clear();
            dealerCards.clear();
            playerCardsPanel.removeAll();
            dealerCardsPanel.removeAll();
            
            // Deal initial cards
            playerCards.add(drawCard());
            playerCards.add(drawCard());
            dealerCards.add(drawCard());
            dealerCards.add(drawCard());
            
            updateDisplay(false);
            
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
            newGameButton.setEnabled(false);
            betField.setEditable(false);
            
            // Check for blackjack
            if (calculateScore(playerCards) == 21) {
                stand();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kérlek írj be egy érvényes számot a tét mezőbe!", "Hiba", JOptionPane.ERROR_MESSAGE);
            betField.setText("10");
        }
    }
    
    private void hit() {
        playerCards.add(drawCard());
        updateDisplay(false);
        
        if (calculateScore(playerCards) > 21) {
            endGame("Túllépted a 21-et! Vesztettél!");
        }
    }
    
    private void stand() {
        // Dealer draws until 17 or higher
        while (calculateScore(dealerCards) < 17) {
            dealerCards.add(drawCard());
        }
        
        updateDisplay(true);
        
        int playerScore = calculateScore(playerCards);
        int dealerScore = calculateScore(dealerCards);
        
        if (dealerScore > 21) {
            endGame("Az osztó túllépte a 21-et! Nyertél!");
            currentUser.balance = (currentUser.balance + bet * 2); // Visszakapja + nyeremény
        } else if (playerScore > dealerScore) {
            endGame("Nyertél!");
            currentUser.balance = (currentUser.balance + bet * 2); // Visszakapja + nyeremény
        } else if (playerScore < dealerScore) {
            endGame("Vesztettél!");
            // Nem kap vissza semmit, már levonva
        } else {
            endGame("Döntetlen!");
            currentUser.balance = (currentUser.balance + bet); // Visszakapja a tétet
        }
        
        balanceLabel.setText("Egyenleg: $" + currentUser.balance);
    }
    
    private void endGame(String message) {
        JOptionPane.showMessageDialog(this, message, "Játék vége", JOptionPane.INFORMATION_MESSAGE);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        newGameButton.setEnabled(true);
        betField.setEditable(true);
    }
    
    private int drawCard() {
        return random.nextInt(11) + 1; // 1-11
    }
    
    private int calculateScore(List<Integer> cards) {
        int score = 0;
        int aces = 0;
        
        for (int card : cards) {
            if (card == 1) {
                aces++;
                score += 11;
            } else {
                score += card;
            }
        }
        
        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }
        
        return score;
    }
    
    private void updateDisplay(boolean showDealerCards) {
        playerCardsPanel.removeAll();
        dealerCardsPanel.removeAll();
        
        for (int card : playerCards) {
            playerCardsPanel.add(createCardLabel(card));
        }
        
        for (int i = 0; i < dealerCards.size(); i++) {
            if (i == 0 && !showDealerCards) {
                dealerCardsPanel.add(createCardLabel(-1)); // Hidden card
            } else {
                dealerCardsPanel.add(createCardLabel(dealerCards.get(i)));
            }
        }
        
        playerScoreLabel.setText("Pontszám: " + calculateScore(playerCards));
        if (showDealerCards) {
            dealerScoreLabel.setText("Pontszám: " + calculateScore(dealerCards));
        } else {
            dealerScoreLabel.setText("Pontszám: ?");
        }
        
        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();
        dealerCardsPanel.revalidate();
        dealerCardsPanel.repaint();
    }
    
    private JLabel createCardLabel(int value) {
        String text = value == -1 ? "?" : String.valueOf(value);
        JLabel card = new JLabel(text, SwingConstants.CENTER);
        card.setFont(new Font("SansSerif", Font.BOLD, 24));
        card.setForeground(Color.BLACK);
        card.setBackground(Color.WHITE);
        card.setOpaque(true);
        card.setPreferredSize(new Dimension(60, 90));
        card.setBorder(BorderFactory.createLineBorder(GOLD_COLOR, 2));
        return card;
    }
}
