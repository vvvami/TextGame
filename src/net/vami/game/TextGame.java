package net.vami.game;


import net.vami.game.world.Game;
import net.vami.interactables.ai.AttackTask;
import net.vami.interactables.ai.Brain;
import net.vami.interactables.ai.EquipTask;
import net.vami.interactables.ai.TargetTask;

public class TextGame {
    public static void main(String[] args) {
        Game.initializeGame();
        Game.startGame();
    }


    public static final String saveFileDirectory = "data/saves/";

}