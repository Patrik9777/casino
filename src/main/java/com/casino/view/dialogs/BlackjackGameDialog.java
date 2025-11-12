package com.casino.view.dialogs;

import com.casino.controllers.BlackjackGameController;
import com.casino.controllers.UserController;
import com.casino.models.User;
import com.casino.view.frames.MainMenuFrame;
import com.casino.view.panels.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Getter
public class BlackjackGameDialog extends JDialog {
    private User currentUser;
    private JLabel balanceLabel;

    // Child panels
    @Autowired
    private BlackjackGameMainPanel mainPanel;

    @Autowired
    private BlackjackGameController blackjackGameController;

    @Autowired
    private UserController userController;

    // Game Logic
    private List<Integer> playerCards = new ArrayList<>();
    private List<Integer> dealerCards = new ArrayList<>();
    private int bet = 0;
    private Random random = new Random();

    public void setup(MainMenuFrame parent, User user, JLabel balanceLabel) {
        setTitle("🎴 Blackjack");
        setModal(true);
        this.currentUser = user;
        this.balanceLabel = balanceLabel;
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    @PostConstruct
    private void initPanel() {
        setUp();
        initUI();
    }

    private void setUp() {
        setSize(800, 600);
        setUndecorated(true);
    }

    private void initUI() {
        mainPanel.headerPanel.closeButton.addActionListener(e -> this.dispose());
        mainPanel.controlPanel.newGameButton.addActionListener(e -> startNewGame());
        mainPanel.controlPanel.hitButton.addActionListener(e -> hit());
        mainPanel.controlPanel.standButton.addActionListener(e -> stand());
        setContentPane(mainPanel);
    }

    private void startNewGame() {
        try {
            bet = Integer.parseInt(mainPanel.controlPanel.betField.getText());
            if (bet <= 0) {
                showError("A tétnek legalább $1-nak kell lennie!");
                return;
            }
            if (bet > currentUser.balance) {
                showError("Nincs elég egyenleged! Jelenlegi egyenleg: $" + currentUser.balance);
                return;
            }

            int newBalance = (currentUser.balance - bet);
            userController.setNewBalance(currentUser, newBalance);
            balanceLabel.setText("Egyenleg: $" + newBalance);

            playerCards.clear();
            dealerCards.clear();
            mainPanel.gamePanel.playerPanel.playerCardsPanel.removeAll();
            mainPanel.gamePanel.dealerPanel.dealerCardsPanel.removeAll();

            // Deal initial cards
            playerCards.add(blackjackGameController.drawCard());
            playerCards.add(blackjackGameController.drawCard());
            dealerCards.add(blackjackGameController.drawCard());
            dealerCards.add(blackjackGameController.drawCard());

            updateDisplay(false);

            mainPanel.controlPanel.hitButton.setEnabled(true);
            mainPanel.controlPanel.standButton.setEnabled(true);
            mainPanel.controlPanel.newGameButton.setEnabled(false);
            mainPanel.controlPanel.betField.setEditable(false);

            // Check for blackjack
            if (blackjackGameController.calculateScore(playerCards) == 21) {
                stand();
            }
        } catch (NumberFormatException ex) {
            showError("Kérlek írj be egy érvényes számot a tét mezőbe!");
            mainPanel.controlPanel.betField.setText("10");
        }
    }


    private void hit() {
        playerCards.add(blackjackGameController.drawCard());
        updateDisplay(false);

        if (blackjackGameController.calculateScore(playerCards) > 21) {
            endGame("Túllépted a 21-et! Vesztettél!");
        }
    }

    private void stand() {
        while (blackjackGameController.calculateScore(dealerCards) < 17) {
            dealerCards.add(blackjackGameController.drawCard());
        }

        updateDisplay(true);

        int playerScore = blackjackGameController.calculateScore(playerCards);
        int dealerScore = blackjackGameController.calculateScore(dealerCards);

        int newBalance = currentUser.balance;
        if (dealerScore > 21) {
            endGame("Az osztó túllépte a 21-et! Nyertél!");
            newBalance = (newBalance + bet * 2);
        } else if (playerScore > dealerScore) {
            endGame("Nyertél!");
            newBalance = (newBalance + bet * 2);
        } else if (playerScore < dealerScore) {
            endGame("Vesztettél!");
        } else {
            endGame("Döntetlen!");
            newBalance = (newBalance + bet);
        }
        userController.setNewBalance(currentUser, newBalance);
        balanceLabel.setText("Egyenleg: $" + currentUser.balance);
    }

    private void endGame(String message) {
        JOptionPane.showMessageDialog(this, message, "Játék vége", JOptionPane.INFORMATION_MESSAGE);
        mainPanel.controlPanel.hitButton.setEnabled(false);
        mainPanel.controlPanel.standButton.setEnabled(false);
        mainPanel.controlPanel.newGameButton.setEnabled(true);
        mainPanel.controlPanel.betField.setEditable(true);
    }


    private void updateDisplay(boolean showDealerCards) {
        mainPanel.gamePanel.playerPanel.playerCardsPanel.removeAll();
        mainPanel.gamePanel.dealerPanel.dealerCardsPanel.removeAll();

        for (int card : playerCards) {
            mainPanel.gamePanel.playerPanel.playerCardsPanel.add(createCardLabel(card));
        }
        for (int i = 0; i < dealerCards.size(); i++) {
            if (i == 0 && !showDealerCards) {
                mainPanel.gamePanel.dealerPanel.dealerCardsPanel.add(createCardLabel(-1));
            } else {
                mainPanel.gamePanel.dealerPanel.dealerCardsPanel.add(createCardLabel(dealerCards.get(i)));
            }
        }

        mainPanel.gamePanel.playerPanel.playerScoreLabel.setText("Pontszám: " +blackjackGameController. calculateScore(playerCards));
        if (showDealerCards) {
            mainPanel.gamePanel.dealerPanel.dealerScoreLabel.setText("Pontszám: " + blackjackGameController.calculateScore(dealerCards));
        } else {
            mainPanel. gamePanel.dealerPanel.dealerScoreLabel.setText("Pontszám: ?");
        }

        mainPanel.gamePanel.playerPanel.playerCardsPanel.revalidate();
        mainPanel.gamePanel.playerPanel.playerCardsPanel.repaint();
        mainPanel.gamePanel.dealerPanel.dealerCardsPanel.revalidate();
        mainPanel.gamePanel.dealerPanel.dealerCardsPanel.repaint();
    }

    private JLabel createCardLabel(int value) {
        String text = value == -1 ? "?" : String.valueOf(value);
        JLabel card = new JLabel(text, SwingConstants.CENTER);
        card.setFont(new Font("SansSerif", Font.BOLD, 24));
        card.setForeground(Color.BLACK);
        card.setBackground(Color.WHITE);
        card.setOpaque(true);
        card.setPreferredSize(new Dimension(60, 90));
        card.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
        return card;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Hiba", JOptionPane.ERROR_MESSAGE);
    }
}
