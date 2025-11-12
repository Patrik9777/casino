package com.casino.view.panels;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@org.springframework.stereotype.Component
@Getter
public class DealerPanel extends JPanel {

    public JLabel dealerLabel;
    public JLabel dealerScoreLabel;
    public JPanel dealerCardsPanel;

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
        dealerLabel = new JLabel("Osztó");
        dealerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        dealerLabel.setForeground(new Color(230, 200, 150));
        dealerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dealerScoreLabel = new JLabel("Pontszám: 0");
        dealerScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        dealerScoreLabel.setForeground(new Color(230, 200, 150));
        dealerScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dealerCardsPanel = new JPanel();
        dealerCardsPanel.setOpaque(false);
        dealerCardsPanel.setPreferredSize(new Dimension(700, 120));
        dealerCardsPanel.setMaximumSize(new Dimension(700, 120));

        add(dealerLabel);
        add(Box.createVerticalStrut(5));
        add(dealerScoreLabel);
        add(Box.createVerticalStrut(10));
        add(dealerCardsPanel);
    }
}
