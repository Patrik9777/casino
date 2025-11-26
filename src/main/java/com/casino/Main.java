package com.casino;

import com.casino.view.frames.CasinoLoginFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class Main {

    public static CasinoLoginFrame loginFrame;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Main.class).headless(false).run(args);

        SwingUtilities.invokeLater(() -> {
            loginFrame = context.getBean(CasinoLoginFrame.class);
            loginFrame.setVisible(true);
        });
    }
}
