package com.casino;

import com.casino.view.CasinoLoginFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.SwingUtilities;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Main.class).headless(false).run(args);

        SwingUtilities.invokeLater(() -> {
            CasinoLoginFrame appFrame = context.getBean(CasinoLoginFrame.class);
            appFrame.setVisible(true);
        });
    }
}
