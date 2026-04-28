package net.vami.util;

import net.vami.game.Game;
import net.vami.game.display.panel.custom.GamePanel;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.item.Item;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class TextUtil {

    public static final Color defaultTextColor = Color.white;
    public static final BiMap<String, Color> colorMap = new BiMap<>();
    public static final String code = "&";

    public static void display(Interactable source, String text, Object ... args) {
        display(source, defaultTextColor, text, args);
    }

    public static void display(@Nullable Interactable source, Color color, String text, Object ... args) {
        if (source == null
                || source instanceof Item
                || source.getPos().equals(Game.player.getPos())) {

            GamePanel.setParentTextColor(color);
            Game.getDisplay().display(String.format(text, args), color);
        }
    }

    public static void display(String text, Color color, Object ... args) {
        display(null, color, text, args);

    }

    public static void display(String text, Object ... args) {
        display(text, defaultTextColor, args);
    }

    public static String setColor(String input, Color color) {
        input = colorMap.getKey(color) + input + colorMap.getKey(null);
        return input;
    }

    public static void registerColorMap() {
        colorMap.put(code + "r", null);
        colorMap.put(code + "1", Color.pink);
        colorMap.put(code + "2", Color.black);
        colorMap.put(code + "3", Color.blue);
        colorMap.put(code + "4", Color.cyan);
        colorMap.put(code + "5", Color.green);
        colorMap.put(code + "6", Color.yellow);
        colorMap.put(code + "7", Color.orange);
        colorMap.put(code + "8", Color.red);
        colorMap.put(code + "9", Color.magenta);
    }

}
