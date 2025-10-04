package net.vami.util;

import net.vami.game.Game;
import net.vami.game.display.panels.custom.GamePanel;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.items.Item;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TextUtil {

    public static final Color defaultTextColor = Color.white;
    public static BiMap<String, Color> colorMap = new BiMap<>();
    public static final String code = "&";

    public static void display(Interactable source, String text, Object ... args) {
        display(source, defaultTextColor, text, args);
    }

    public static void display(@Nullable Interactable source, Color color, String text, Object ... args) {
        if (source == null || source instanceof Item || source.getPos().equals(Game.player.getPos())) {
            int counter = 0;
            GamePanel.setParentTextColor(color);
            Game.getDisplay().display(String.format(text, args), color);

            if (source != null && source.getNode() != null) {
                counter = source.getNode().getEntities().size();
            }

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
