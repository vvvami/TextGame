package net.vami.game.world;

import net.vami.game.interactions.Ability;
import net.vami.game.interactions.Action;
import net.vami.interactables.ai.AllyHandler;
import net.vami.interactables.ai.EnemyHandler;
import net.vami.interactables.ai.PlayerHandler;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Player;
import net.vami.interactables.items.ItemEquipable;

import java.util.List;
import java.util.Scanner;

public abstract class Game {

    private static boolean endGame = false;
    public static final Player player = new Player(namePlayer(), new Position(0,0,0),
            1, 20, 2, 0, Ability.BURN);

    public static void startGame() {
        if (!getCurrentNode().getInteractables().contains(player)) {
            getCurrentNode().addInteractable(player);
        }

        EnemyHandler.Generate(player.getPosition());
        AllyHandler.Generate(player.getPosition());

        do {
            if (!getCurrentNode().getInteractables().contains(player)) {
                getCurrentNode().addInteractable(player);
            }

            EnemyHandler.enemyAction();
            enemyTurn();

            if (!Game.player.isEnded()) {
                if (!PlayerHandler.read()) {
                    AllyHandler.allyAction();
                    continue;
                }
            }

            playerTurn();
        }
        while (!endGame);

    }

    static void enemyTurn() {
        itemTicker();
        enemyTicker();
    }

    static void playerTurn() {
        allyTicker();
    }

    static void itemTicker() {
        List<Entity> entities = Node.getEntities();
        for (Entity entity : entities) {
            List<ItemEquipable> itemEquipables = entity.getEquippedItems();

            for (ItemEquipable item : itemEquipables)
            {
                item.turn();
            }
            if (entity.getHeldItem() != null) {
                entity.getHeldItem().turn();
            }
        }
    }

    static void enemyTicker() {
        List<Entity> enemies = Node.getEnemies();
        for (Entity enemy : enemies) {

            if (!enemy.isEnded()) {
                enemy.turn();
            }

            else {
                System.out.println(enemy.getName() + " has died!");
                getCurrentNode().removeInteractable(enemy);

                if (Node.getEnemies().isEmpty()) {
                    endGame = true;
                }
            }
        }
    }

    static void allyTicker() {
        List<Entity> allies = Node.getAllies();
        for (Entity ally : allies) {

            if (!ally.isEnded()) {
                ally.turn();
            }

            else {
                System.out.println(ally.getName() + " has died!");
                getCurrentNode().removeInteractable(ally);

                if (ally.equals(Game.player)) {
                    System.out.println("Game Over!");
                    endGame = true;
                }
            }
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
