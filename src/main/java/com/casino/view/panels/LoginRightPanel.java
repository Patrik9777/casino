package com.casino.view.panels;

import com.casino.view.Constants;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static com.casino.helpers.SwigHelper.drawCornerDecoration;

@Component
@Getter
public class LoginRightPanel extends JPanel {

    @Override
    public void addNotify() {
        super.addNotify();
        // Now parent/container/root exists
        var root = SwingUtilities.getRoot(this);
        if (root != null) {
            int width = root.getWidth() / 2;
            int height = root.getHeight();
            setBounds(width, 0, width, height);
            setBackground(new Color(0, 0, 0, 0));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        // Sötét háttér finom mintázattal
        g2d.setColor(new Color(15, 15, 20));
        g2d.fillRect(0, 0, width, height);

        // Finom rácsmintázat
        g2d.setColor(new Color(218, 165, 32, 5));
        for (int i = 0; i < width; i += 30) {
            g2d.drawLine(i, 0, i, height);
        }
        for (int i = 0; i < height; i += 30) {
            g2d.drawLine(0, i, width, i);
        }

        // Középső körök díszítésként
        g2d.setColor(new Color(218, 165, 32, 10));
        g2d.fillOval(50, 50, 150, 150);
        g2d.setColor(new Color(218, 165, 32, 15));
        g2d.fillOval(200, 200, 200, 200);
        g2d.setColor(new Color(218, 165, 32, 20));
        g2d.fillOval(100, 350, 180, 180);

        // Középre igazított üdvözlő szöveg
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        String welcome = "ÜDVÖZÖLJÜK!";
        int welcomeWidth = g2d.getFontMetrics().stringWidth(welcome);

        // Szöveg árnyéka
        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.drawString(welcome, (width - welcomeWidth) / 2 + 2, height / 2 + 2);

        // Arany színű szöveg gradiensekkel
        GradientPaint goldGradient = new GradientPaint(0, 0, Constants.GOLD_LIGHT, 0, 40, Constants.GOLD_DARK);
        g2d.setPaint(goldGradient);
        g2d.drawString(welcome, (width - welcomeWidth) / 2, height / 2);

        // Aláíró vonal
        g2d.setColor(Constants.GOLD_COLOR);
        g2d.setStroke(new BasicStroke(2f));
        int lineY = height / 2 + 20;
        g2d.drawLine((width - welcomeWidth) / 2 - 20, lineY,
                (width + welcomeWidth) / 2 + 20, lineY);

        // Díszelemek a sarkokban
        drawCornerDecoration(g2d, 20, height - 100, 80, 80, false);
        drawCornerDecoration(g2d, width - 100, height - 100, 80, 80, true);

        g2d.dispose();
    }
}
