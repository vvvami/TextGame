package net.vami.game.display.main;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        this.setTitle("Text Game");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(420, 420);
        this.getContentPane().setBackground(Color.black);

        ImageIcon frameIcon = new ImageIcon("assets/textures/test.png");
        this.setIconImage(frameIcon.getImage());
    }
}
