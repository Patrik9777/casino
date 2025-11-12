package com.casino.view.panels;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@org.springframework.stereotype.Component
@Getter
public class PlayerPanel extends JPanel {

    public JLabel playerLabel;
    public JLabel playerScoreLabel;
    public JPanel playerCardsPanel;

    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
    }

    private void setUp() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void initComponents() {
        playerLabel = new JLabel("Te");
        playerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        playerLabel.setForeground(new Color(230, 200, 150));
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerScoreLabel = new JLabel("Pontszám: 0");
        playerScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        playerScoreLabel.setForeground(new Color(230, 200, 150));
        playerScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerCardsPanel = new JPanel();
        playerCardsPanel.setOpaque(false);
        playerCardsPanel.setPreferredSize(new Dimension(700, 120));
        playerCardsPanel.setMaximumSize(new Dimension(700, 120));

        add(playerLabel);
        add(Box.createVerticalStrut(5));
        add(playerScoreLabel);
        add(Box.createVerticalStrut(10));
        add(playerCardsPanel);
    }
}
