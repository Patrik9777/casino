package com.casino.view.panels;

import com.casino.view.Constants;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static com.casino.helpers.SwigHelper.createButton;

@Component
@Getter
public class BlackjackControlPanel extends JPanel {
    public JTextField betField;
    public JButton newGameButton;
    public JButton hitButton;
    public JButton standButton;
    public JLabel betLabel;

    @PostConstruct
    private void initPanel() {
        setUp();
        initComponents();
    }

    private void setUp() {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
    }

    public void initComponents() {
        betLabel = new JLabel("Tét: $");
        betLabel.setForeground(Constants.TEXT_COLOR);
        betLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        betField = new JTextField("10", 8);
        betField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        newGameButton = createButton("Új játék");
        hitButton = createButton("Lapot kérek");
        standButton = createButton("Megállok");

        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        add(betLabel);
        add(betField);
        add(newGameButton);
        add(hitButton);
        add(standButton);
    }
}
