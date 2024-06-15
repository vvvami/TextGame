package net.vami.game.world;

import net.vami.interactables.interactions.Action;
import net.vami.interactables.ai.AllyHandler;
import net.vami.interactables.ai.EnemyHandler;
import net.vami.interactables.ai.PlayerHandler;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Player;
import net.vami.interactables.items.ItemEquipable;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public abstract class Game {
    public static final Player player = new Player(namePlayer(),
            new Entity.Attributes()
                    .level(4));

    private static boolean endGame = false;


    public static void startGame() {

        EnemyHandler.Generate();
        AllyHandler.Generate();

        do {

            EnemyHandler.enemyAction();
            enemyTurn();

            if (!Game.player.isEnded()) {
                if (!PlayerHandler.read()) {
                    continue;
                }
            }
            AllyHandler.allyAction();
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
        for (Entity enemy : Node.getEnemies()) {
            if (!enemyEndedCheck(enemy)) {
                enemy.turn();
            }
        }

        for (Entity ally : Node.getAllies()) {
            allyEndedCheck(ally);
        }
    }

    static void allyTicker() {
        for (Entity ally : Node.getAllies()) {
            if (!allyEndedCheck(ally)) {
                ally.turn();
            }
        }

        for (Entity enemy : Node.getEnemies()) {
            enemyEndedCheck(enemy);
        }
    }


    public static Node getCurrentNode() {

        return Node.getNodeFromPosition(gamePos());
    }

    public static void initializeGame() {



        Node.initializeNodes();
        Action.registerActionSynonyms();
        if (Node.getNodeFromPosition(player.getPos()) != null) {
            Node.getNodeFromPosition(player.getPos()).addInteractable(player);
        }
    }

    public static String namePlayer() {
        System.out.println("Enter your name, traveler:");
        Scanner playerNameScanner = new Scanner(System.in);
        return playerNameScanner.nextLine();
    }

    public static Position gamePos() {
        return player.getPos();
    }

    private static boolean allyEndedCheck(Entity ally) {
            if (ally.isEnded()) {
                System.out.println(ally.getName() + " has died!");
                getCurrentNode().removeInteractable(ally);

                if (ally.equals(Game.player)) {
                    System.out.println("Game Over!");
                    endGame = true;
                }
                return true;
            }
            return false;
    }

    private static boolean enemyEndedCheck(Entity enemy) {
        if (enemy.isEnded()) {
            System.out.println(enemy.getName() + " has died!");
            getCurrentNode().removeInteractable(enemy);
            if (Node.getEnemies().isEmpty()) {
                endGame = true;
            }
            return true;
        }
        return false;
    }

}
