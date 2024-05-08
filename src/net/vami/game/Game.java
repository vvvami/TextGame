package net.vami.game;

import net.vami.interactables.Entity;
import net.vami.interactables.Player;

import java.util.List;
import java.util.Scanner;

public abstract class Game extends GameInitializer {

    private static Scanner playerNameScanner = new Scanner(System.in);
    private static String playerName = playerNameScanner.nextLine();
    public static final Player player = new Player(playerName, new Position(0,0,0), 1, 1000, 1, 0, Ability.HEAL);
    private static boolean endGame = false;

    public static void StartGame() {
        EnemyHandler.Generate(player.getPosition());

        do {

            EnemyHandler.enemyAction();

            if (Game.player.isEnded()) {
                PlayerInterpreter.read();
            }

            Turn();
        }
        while (!endGame);

    }

    private static void Turn() {
        List<Entity> entities = Node.getEntities();
        for (Entity entity : entities) {
            if (entity.isEnded()) {
                entity.entityTurn();
            }
            else {
                System.out.println(entity.getName() + " has died!");
                if (entity.equals(Game.player)) {
                    System.out.println("net.vami.game.Game Over!");
                    endGame = true;
                }
                else {
                    getCurrentNode().removeInteractable(entity);
                }
            }
        }
        if (Node.getEnemies().isEmpty()) {
            endGame = true;
        }
    }


    public static Node getCurrentNode() {
        return Node.getNodeFromPosition(player.getPosition());
    }


}
