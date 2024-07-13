package net.vami.game;


import net.vami.game.world.Game;

public class TextGame {
    public static void main(String[] args) {
        Game.initializeGame();
        Game.startGame();
    }


    public static final String saveFileDirectory = "data/saves/";

}