package net.vami.game.world;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.ai.PlayerHandler;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Player;
import net.vami.game.interactables.interactions.abilities.HypnosisAbility;
import net.vami.game.interactables.interactions.abilities.RageAbility;
import net.vami.game.interactables.items.ItemEquipable;

import java.util.List;
import java.util.Scanner;

public abstract class Game {
    public static final Player player = new Player(namePlayer(),
            new Entity.Attributes()
                    .level(5)
                    .ability(new HypnosisAbility()));

    private static boolean endGame = false;


    public static void startGame() {

        EnemyHandler.Generate();
        AllyHandler.Generate();

        do {

            itemTicker();

            EnemyHandler.enemyAction();
            allyTicker();

            if (Game.player.getPos() == Game.gamePos()) {
                    PlayerHandler.read();
            }

            AllyHandler.allyAction();
            enemyTicker();

        }
        while (!endGame);

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
                if (ally.equals(Game.player)) {
                    System.out.println("Game Over!");
                    endGame = true;
                }
                ally.kill();
                return true;
            }
            return false;
    }

    private static boolean enemyEndedCheck(Entity enemy) {
        if (enemy.isEnded()) {
            System.out.println(enemy.getName() + " has died!");
            enemy.kill();
            if (Node.getEnemies().isEmpty()) {
                endGame = true;
            }
            return true;
        }
        return false;
    }

    public static List<Interactable> getInteractables() {
        return Game.getCurrentNode().getInteractables();
    }

}
