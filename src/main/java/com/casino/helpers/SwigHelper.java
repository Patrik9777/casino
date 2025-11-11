package com.casino.helpers;

import com.casino.view.Constants;

import javax.swing.*;
import java.awt.*;

public final class SwigHelper {

    public static void styleTextField(JTextField field) {
        field.setBackground(Constants.FIELD_BG);
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Constants.FIELD_BORDER, 1),
                BorderFactory.createEmptyBorder(0, 15, 0, 15)
        ));
        field.setCaretColor(Constants.GOLD_LIGHT);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setSelectionColor(new Color(218, 165, 32, 100));
        field.setSelectedTextColor(Color.WHITE);
    }

    public static void drawCornerDecoration(Graphics2D g2d, int x, int y, int size, int thickness, boolean topLeft) {
        g2d.setStroke(new BasicStroke(2f));
        g2d.setColor(Constants.GOLD_COLOR);

        int arc = size / 2;

        // Külső sarok
        if (topLeft) {
            // Bal felső sarok
            g2d.drawLine(x, y + arc, x, y);
            g2d.drawLine(x, y, x + arc, y);

            // Belső vonalak
            int innerOffset = 10;
            g2d.drawLine(x + innerOffset, y + arc, x + innerOffset, y + innerOffset);
            g2d.drawLine(x + innerOffset, y + innerOffset, x + arc, y + innerOffset);
        } else {
            // Jobb felső sarok
            g2d.drawLine(x + size - arc, y, x + size, y);
            g2d.drawLine(x + size, y, x + size, y + arc);

            // Belső vonalak
            int innerOffset = 10;
            g2d.drawLine(x + size - innerOffset, y + innerOffset, x + size - innerOffset, y + innerOffset);
            g2d.drawLine(x + size - innerOffset, y + innerOffset, x + size - arc + innerOffset, y + innerOffset);
        }
    }
}
