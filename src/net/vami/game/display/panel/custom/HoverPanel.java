package net.vami.game.display.panel.custom;

import net.vami.game.display.panel.GameFrame;
import net.vami.game.display.panel.HoverInfo;
import net.vami.util.TextUtil;

import javax.swing.*;
import java.awt.*;


public class HoverPanel extends JPanel {
    private boolean showing = false;
    private int popupX;
    private int popupY;
    private HoverInfo hoverInfo;

    private int popupWidth = 300;
    private int popupHeight = 140;

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

    private void drawColoredString(
            Graphics2D g2,
            String text,
            int x,
            int y,
            Color defaultColor
    ) {

        Color color = defaultColor;
        int drawX = x;
        int chunkStart = 0;
        int i = 0;

        while (i < text.length() - 1) {
            String colorCode = text.substring(i, i + 2);

            if (TextUtil.colorMap.containsKey(colorCode)) {
                if (chunkStart < i) {
                    String chunk = text.substring(chunkStart, i);
                    g2.setColor(color);
                    g2.drawString(chunk, drawX, y);
                    drawX += g2.getFontMetrics().stringWidth(chunk);
                }

                Color mappedColor = TextUtil.colorMap.get(colorCode);

                // null means reset to default white
                color = mappedColor != null
                        ? mappedColor
                        : TextUtil.defaultTextColor;

                i += 2;
                chunkStart = i;
            } else {
                i++;
            }
        }

        if (chunkStart < text.length()) {
            String chunk = text.substring(chunkStart);
            g2.setColor(color);
            g2.drawString(chunk, drawX, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!showing || hoverInfo == null) return;

        Graphics2D g2 = (Graphics2D) g.create();

        String[] descriptionLines = hoverInfo.getDescription().split("\n");

        int padding = 15;
        int titleY = popupY + 35;
        int descriptionStartY = popupY + 65;
        int lineSpacing = 22;

        int titleAreaHeight = 50;
        int descriptionHeight = descriptionLines.length * lineSpacing;

        popupHeight = titleAreaHeight + descriptionHeight + padding;

        g2.setColor(new Color(30, 30, 30, 230));
        g2.fillRoundRect(popupX, popupY, popupWidth, popupHeight, 16, 16);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(popupX, popupY, popupWidth, popupHeight, 16, 16);

        g2.setFont(new Font(GameFrame.FONT, Font.BOLD, 22));

        drawColoredString(
                g2,
                hoverInfo.getTitle(),
                popupX + padding,
                titleY,
                TextUtil.defaultTextColor
        );

        g2.setFont(new Font(GameFrame.FONT, Font.PLAIN, 16));

        int textY = descriptionStartY;

        for (String line : descriptionLines) {
            drawColoredString(
                    g2,
                    line,
                    popupX + padding,
                    textY,
                    TextUtil.defaultTextColor
            );

            textY += lineSpacing;
        }

        g2.dispose();
    }
}
