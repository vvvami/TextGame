package net.vami.game.display.panel.custom;

import net.vami.game.display.panel.GameFrame;
import net.vami.game.display.panel.HoverInfo;

import javax.swing.*;
import java.awt.*;


public class HoverPanel extends JPanel {
    private boolean showing = false;
    private int popupX;
    private int popupY;
    private HoverInfo hoverInfo;

    private final int popupWidth = 300;
    private final int popupHeight = 140;

    public HoverPanel() {
        setOpaque(false);
    }

    public void showAt(int x, int y, HoverInfo hoverInfo) {
        this.popupX = x;
        this.popupY = y;
        this.hoverInfo = hoverInfo;
        this.showing = true;
        repaint();
    }

    public void hidePopup() {
        this.showing = false;
        this.hoverInfo = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!showing || hoverInfo == null) return;

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(30, 30, 30, 230));
        g2.fillRoundRect(popupX, popupY, popupWidth, popupHeight, 16, 16);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(popupX, popupY, popupWidth, popupHeight, 16, 16);

        g2.setFont(new Font(GameFrame.FONT, Font.BOLD, 22));
        g2.drawString(hoverInfo.getTitle(), popupX + 15, popupY + 35);

        g2.setFont(new Font(GameFrame.FONT, Font.PLAIN, 16));

        int textY = popupY + 65;
        for (String line : hoverInfo.getDescription().split("\n")) {
            g2.drawString(line, popupX + 15, textY);
            textY += 22;
        }

        g2.dispose();
    }
}
