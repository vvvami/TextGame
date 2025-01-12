package net.vami;


import net.vami.game.Game;
import net.vami.game.display.panels.GameFrame;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.util.LogUtil;
import net.vami.util.TextUtil;

import java.awt.*;
import java.text.DecimalFormat;

public class TextGame {

    public static void main(String[] args) {
        Game.initializeGame();
        LogUtil.Log("Game initialized");
    }

}