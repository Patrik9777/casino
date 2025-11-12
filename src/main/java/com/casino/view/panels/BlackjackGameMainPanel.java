package com.casino.view.panels;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Getter
public class BlackjackGameMainPanel extends JPanel {

    @Autowired
    public BlackjackHeaderPanel headerPanel;
    @Autowired
    public BlackjackGamePanel gamePanel;
    @Autowired
    public BlackjackControlPanel controlPanel;

    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
    }

    private void setUp() {
        setBackground(new Color(20, 40, 20));
        setLayout(new BorderLayout());
    }

    public void initComponents() {
        add(headerPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
}
