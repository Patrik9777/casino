package com.casino;

import com.casino.view.CasinoLoginFrame;
import javax.swing.SwingUtilities;

/**
 * Main Entry Point - Swing UI verzió
 * Spring Boot MVC kompatibilis struktúra
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CasinoLoginFrame frame = new CasinoLoginFrame();
            frame.setVisible(true);
        });
    }
}
