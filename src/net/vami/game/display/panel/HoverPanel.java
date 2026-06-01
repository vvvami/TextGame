package net.vami.game.display.panel;

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
        if (showing
                && popupX == x
                && popupY == y) {
            return;
        }

        this.popupX = x;
        this.popupY = y;
        this.hoverInfo = hoverInfo;
        this.showing = true;

        calculatePopupSize();
        keepPopupInsidePanel();
        repaint();
    }

    public void hidePopup() {
        this.showing = false;
        this.hoverInfo = null;
        repaint();
    }

    private void keepPopupInsidePanel() {
        int edgeMargin = 10;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int maxX = panelWidth - popupWidth - edgeMargin;
        int maxY = panelHeight - popupHeight - edgeMargin;

        popupX = Math.min(popupX, maxX);
        popupY = Math.min(popupY, maxY);

        popupX = Math.max(popupX, edgeMargin);
        popupY = Math.max(popupY, edgeMargin);
    }

    private void calculatePopupSize() {
        if (hoverInfo == null) return;

        String description = hoverInfo.getDescription();
        String[] descriptionLines = description == null || description.isEmpty()
                ? new String[0]
                : description.split("\\R"); // handles \n, \r\n, etc.

        int padding = 15;
        int lineSpacing = 22;
        int titleAreaHeight = 50;

        FontMetrics titleMetrics = getFontMetrics(titleFont);
        FontMetrics descriptionMetrics = getFontMetrics(descriptionFont);

        String title = hoverInfo.getTitle() == null ? "" : hoverInfo.getTitle();

        int titleWidth = titleMetrics.stringWidth(title);

        int maxDescriptionWidth = 0;

        for (String line : descriptionLines) {
            int lineWidth = descriptionMetrics.stringWidth(line);
            maxDescriptionWidth = Math.max(maxDescriptionWidth, lineWidth);
        }

        int maxTextWidth = Math.max(titleWidth, maxDescriptionWidth);

        int minWidth = 200;

        popupWidth = Math.max(minWidth, maxTextWidth + padding * 2);
        popupHeight = titleAreaHeight + descriptionLines.length * lineSpacing + padding;
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

    private static final Color hoverColor = new Color(30, 30, 30, 230);
    private static final Font titleFont = new Font(GameFrame.FONT, Font.BOLD, 22);
    private static final Font descriptionFont = new Font(GameFrame.FONT, Font.PLAIN, 16);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!showing || hoverInfo == null) return;

        // --- RENDERING HINTS ---
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        g2.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON
        );

        g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );

        // --- AUTO-SIZING FOR DISPLAY ---

        String[] descriptionLines = hoverInfo.getDescription().split("\n");

        int padding = 15;
        int titleY = popupY + 35;
        int descriptionStartY = popupY + 65;
        int lineSpacing = 22;

        // --- DRAWING THE ACTUAL RECT AND TEXT ---

        g2.setColor(hoverColor);
        g2.fillRoundRect(popupX, popupY, popupWidth, popupHeight, 16, 16);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(popupX, popupY, popupWidth, popupHeight, 16, 16);

        g2.setFont(titleFont);

        drawColoredString(
                g2,
                hoverInfo.getTitle(),
                popupX + padding,
                titleY,
                TextUtil.defaultTextColor
        );

        g2.setFont(descriptionFont);

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
