package com.casino.view.panels;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Getter
public class LoginRightPanel extends JPanel {

    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
    }

    private void setUp() {
        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
    }

    private void initComponents() {

    }
}
