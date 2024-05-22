package net.vami.game;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Player;

import java.util.List;
import java.util.Scanner;

public abstract class Game {

    private static boolean endGame = false;
    public static final Player player = new Player(namePlayer(), new Position(0,0,0), 1, 1000, 1, 0, Ability.BURN);

    public static void startGame() {
        EnemyHandler.Generate(player.getPosition());
        do {
            if (!getCurrentNode().getInteractables().contains(player)) {
                getCurrentNode().addInteractable(player);
            }

            EnemyHandler.enemyAction();

            if (!Game.player.isEnded()) {
                if (!PlayerInterpreter.read()) {
                    continue;
                }
            }

            Turn();
        }
        while (!endGame);

    }

    private static void Turn() {
        List<Entity> entities = Node.getEntities();
        for (Entity entity : entities) {
            if (!entity.isEnded()) {
                entity.turn();
            }
            else {
                System.out.println(entity.getName() + " has died!");
                if (entity.equals(Game.player)) {
                    System.out.println("Game Over!");
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

    public static void initializeGame() {
        Node.initializeNodes();
        Action.registerActionSynonyms();
    }

    static String namePlayer() {
        System.out.println("Enter your name, traveler:");
        Scanner playerNameScanner = new Scanner(System.in);
        return playerNameScanner.nextLine();
    }

}
