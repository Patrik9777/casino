package com.casino.view.panels;

import com.casino.view.Constants;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Getter
public class ToRegisterPanel extends JPanel {

    public JLabel regText;
    public JLabel registerLink;

    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
    }

    private void setUp() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setOpaque(false);
        setBounds(50, 390, 300, 20);
    }

    private void initComponents() {
        regText = new JLabel("Nincs még fiókod? ");
        regText.setForeground(Constants.TEXT_COLOR);
        regText.setFont(new Font("Arial", Font.PLAIN, 12));
        add(regText);

        registerLink = new JLabel("Regisztrálj most!");
        registerLink.setForeground(Constants.LINK_COLOR);
        registerLink.setFont(new Font("Arial", Font.BOLD, 12));
        registerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(registerLink);
    }
}
