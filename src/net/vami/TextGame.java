package net.vami;


import net.vami.game.Game;
import net.vami.game.display.sound.Sound;

public class TextGame {
    public static void main(String[] args) {
        Game.initializeGame();
        Game.playMusic(Sound.HUDDLED, 65);
        Game.startGame();
    }
}