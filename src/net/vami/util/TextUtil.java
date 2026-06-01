package net.vami.util;

import net.vami.game.Game;
import net.vami.game.display.panel.GamePanel;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.item.ItemInstance;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.text.DecimalFormat;

public class TextUtil {

    public static final Color defaultTextColor = Color.white;
    public static final BiMap<String, Color> colorMap = new BiMap<>();
    public static final String COLOR_CODE = "&";

    public static void showText(@Nullable Interactable source, Color color, String text, Object ... args) {
        if (source == null
                || source instanceof ItemInstance
                || source.getPos().equals(Game.player.getPos())) {

            GamePanel.setParentTextColor(color);

            Game.getPanel().showText(color, text, args);
            LogUtil.log(text, args);

        }
    }

    public static void showText(Interactable source, String text, Object ... args) {
        showText(source, defaultTextColor, text, args);
    }

    public static void showText(Color color, String text, Object ... args) {
        showText(null, color, text, args);

    }

    public static void showText(String text, Object ... args) {
        showText(defaultTextColor, text, args);
    }

    public static String setColor(String input, Color color) {
        input = colorMap.getKey(color) + input + colorMap.getKey(null);
        return input;
    }

    public static String setColor(double num, Color color) {
        String input = new DecimalFormat("#").format(num);
        input = colorMap.getKey(color) + input + colorMap.getKey(null);
        return input;
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
        colorMap.put(COLOR_CODE + "a", Color.gray);
        colorMap.put(COLOR_CODE + "b", Color.lightGray);
        colorMap.put(COLOR_CODE + "c", Color.darkGray);
    }
}
