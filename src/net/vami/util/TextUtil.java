package net.vami.util;

import net.vami.game.Game;
import net.vami.game.display.panel.custom.GamePanel;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.item.ItemInstance;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class TextUtil {

    public static final Color defaultTextColor = Color.white;
    public static final BiMap<String, Color> colorMap = new BiMap<>();
    public static final String COLOR_CODE = "&";
    public static final String HOVER_CODE = "@";

    public static void display(Interactable source, String text, Object ... args) {
        display(source, defaultTextColor, text, args);
    }

    public static void display(@Nullable Interactable source, Color color, String text, Object ... args) {
        if (source == null
                || source instanceof ItemInstance
                || source.getPos().equals(Game.player.getPos())) {

            GamePanel.setParentTextColor(color);
            Game.getDisplay().display(String.format(text, args), color, source);
            LogUtil.Log(text, args);

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

    public static String setHoverable(String input) {
        input = HOVER_CODE + input + HOVER_CODE;
        return input;
    }

    public static boolean isHoverable(String input) {
        return  (input.charAt(0) == HOVER_CODE.charAt(0)
        && input.endsWith(HOVER_CODE));
    }

    public static void registerColorMap() {
        colorMap.put(COLOR_CODE + "r", null);
        colorMap.put(COLOR_CODE + "1", Color.pink);
        colorMap.put(COLOR_CODE + "2", Color.black);
        colorMap.put(COLOR_CODE + "3", Color.blue);
        colorMap.put(COLOR_CODE + "4", Color.cyan);
        colorMap.put(COLOR_CODE + "5", Color.green);
        colorMap.put(COLOR_CODE + "6", Color.yellow);
        colorMap.put(COLOR_CODE + "7", Color.orange);
        colorMap.put(COLOR_CODE + "8", Color.red);
        colorMap.put(COLOR_CODE + "9", Color.magenta);
    }

}
