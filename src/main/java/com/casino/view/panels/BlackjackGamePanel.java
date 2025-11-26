package com.casino.view.panels;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Getter
public class BlackjackGamePanel extends JPanel {

    @Autowired
    public DealerPanel dealerPanel;

    @Autowired
    public PlayerPanel playerPanel;

    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
    }

    private void setUp() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    }

    public void initComponents() {
        add(dealerPanel);
        add(Box.createVerticalStrut(40));
        add(playerPanel);
    }
}
