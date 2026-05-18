package net.vami.game.display.panel;

import net.vami.game.display.panel.custom.GamePanel;
import net.vami.game.display.panel.custom.HoverPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame {
    GamePanel gamePanel;
    HoverPanel hoverPanel;
    public static final String FONT = "Voces";

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
        gamePanel.setOpaque(true);

        hoverPanel = new HoverPanel();

        this.setContentPane(gamePanel);
        this.setGlassPane(hoverPanel);
        hoverPanel.setVisible(true);

        this.pack();
        this.setVisible(true);

    }

    public GamePanel getPanel() {
        return gamePanel;
    }

    public HoverPanel getHoverPanel() { return hoverPanel; }

    public void setHoverPoint(int x, int y) {
        hoverPanel.setBounds(x, y, hoverPanel.getWidth(), hoverPanel.getHeight());
    }

}
