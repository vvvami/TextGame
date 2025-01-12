package net.vami.game.display.panels;

import net.vami.game.display.panels.custom.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GamePanel gamePanel;

    public GameFrame() {

        // Icon
        ImageIcon gameIcon = new ImageIcon("assets/icon.png");

        this.setMinimumSize(new Dimension(0, 0));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(screenSize);
        this.setMaximumSize(screenSize);
        this.setTitle("Text Game");
        this.setFocusable(true);
        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setIconImage(gameIcon.getImage());

        gamePanel = new GamePanel(this);

        this.pack();
        this.setVisible(true);

    }

    public GamePanel getPanel() {
        return gamePanel;
    }



}
