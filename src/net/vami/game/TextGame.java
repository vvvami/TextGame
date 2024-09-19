package net.vami.game;


import net.vami.game.display.main.MainFrame;
import net.vami.game.world.Game;

import javax.swing.*;
import java.awt.*;

public class TextGame {
    public static void main(String[] args) {
        Game.initializeGame();
        MainFrame mainFrame = new MainFrame();
        Game.startGame();
    }
}