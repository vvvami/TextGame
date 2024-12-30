package net.vami;


import net.vami.game.Game;
import net.vami.util.LogUtil;

public class TextGame {
    public static void main(String[] args) {
        Game.initializeGame();
        LogUtil.Log("Game initialized");
//        Game.playMusic(Sound.THINK, 60);
        Game.startGame();
    }
}