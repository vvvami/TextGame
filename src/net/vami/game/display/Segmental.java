package net.vami.game.display;

import net.vami.util.TextUtil;

import java.awt.*;

public class Segmental {
    private String text;
    private Object[] args;
    private Color color;

    public Segmental(String text, Object ... args) {
        this.text = text;
        this.args = args;
        this.color = TextUtil.defaultTextColor;
    }

    public Segmental(Color color, String text, Object... args) {
        this.text = text;
        this.args = args;
        this.color = color;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
