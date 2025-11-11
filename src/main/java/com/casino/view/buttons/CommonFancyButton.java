package com.casino.view.buttons;

import com.casino.view.Constants;

import javax.swing.*;
import java.awt.*;

public class CommonFancyButton extends JButton {

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        // Gomb kitöltése
        if (getModel().isPressed()) {
            // Nyomott állapot
            GradientPaint gradient = new GradientPaint(0, 0, Constants.GOLD_DARK, 0, getHeight(), new Color(139, 101, 8));
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        } else if (getModel().isRollover()) {
            // Egér felette
            GradientPaint gradient = new GradientPaint(0, 0, Constants.GOLD_LIGHT, 0, getHeight(), Constants.GOLD_COLOR);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        } else {
            // Normál állapot
            GradientPaint gradient = new GradientPaint(0, 0, Constants.GOLD_COLOR, 0, getHeight(), Constants.GOLD_DARK);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        }

        // Szegély
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 8, 8);

        // Szöveg középre igazítása
        FontMetrics fm = g2d.getFontMetrics();
        String text = getText();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();

        // Szöveg árnyéka
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(text, x+1, y+1);

        // Fő szöveg
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(text, x, y);

        // Fényhatás a gomb tetején
        if (!getModel().isPressed()) {
            g2d.setColor(new Color(255, 255, 255, 60));
            g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()/2, 8, 8);
        }

        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Ne legyen alapértelmezett szegély
    }
}
