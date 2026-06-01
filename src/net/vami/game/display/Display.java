package net.vami.game.display;

import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.world.Position;
import net.vami.util.TextUtil;

import java.awt.*;

public class Display {
    public static void playSound(Interactable source, Sound sound, int volume) {
        playSound(source, sound, volume, 0);
    }

    public static void playSound(Interactable source, Sound sound, int volume, int msDelay) {
        playSound(source.getPos(), sound, volume, msDelay);
    }

    public static void playSound(Position position, Sound sound, int volume) {
        playSound(position, sound, volume, 0);
    }

    public static void playSound(Position position, Sound sound, int volume, int msDelay) {
        sound.playSound(position, volume, msDelay);
    }

    public static void display(Interactable source, Segmental segment, Sound sound, int volume) {
        Game.getPanel().display(
                source == null ? null : source.getPos(),
                segment.getColor(),
                segment.getText(),
                sound,
                volume,
                segment.getArgs()
        );
    }

    public static void playMusic(Sound sound, int volume) {
        sound.playMusic(volume);
    }

    public static void showText(Interactable source, Color color, String text, Object ... args) {
        TextUtil.showText(source, color, text, args);
    }

    public static void showText(Interactable source, String text, Object ... args) {
        showText(source, TextUtil.defaultTextColor, text, args);
    }

    public static void showText(Color color, String text, Object ... args) {
        showText(null, color, text, args);

    }

    public static void print(Color color, String text, Object ... args) {
        Game.getPanel().print(color, text, args);
    }

    public static void print(String text, Object ... args) {
        print(TextUtil.defaultTextColor, text, args);
    }

    public static void showText(String text, Object ... args) {
        showText(TextUtil.defaultTextColor, text, args);
    }

}