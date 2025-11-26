package com.casino.view.dialogs;

import com.casino.view.panels.RegisterPanel;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.geom.RoundRectangle2D;

@Component
@AllArgsConstructor
@Getter
public class RegisterDialog extends JDialog {

    public RegisterPanel panel;


    @PostConstruct
    private void prepareFrame() {
        setUp();
        initComponents();
    }

    private void setUp() {
        setModal(true);
        setTitle("Regisztráció");
        setSize(400,400);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 400, 400, 20, 20));
    }

    private void initComponents() {
        add(panel);
    }

}
