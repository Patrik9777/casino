package com.casino.view.panels;

import com.casino.view.Constants;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Getter
public class BlackjackHeaderPanel extends JPanel {

    public JLabel titleLabel;
    public JButton closeButton;

    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
    }

    private void setUp() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public void initComponents() {
        titleLabel = new JLabel("🎴 BLACKJACK");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Constants.GOLD_COLOR);

        closeButton = new JButton("✕");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(150, 50, 50));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(40, 40));

        add(titleLabel, BorderLayout.WEST);
        add(closeButton, BorderLayout.EAST);
    }
}
