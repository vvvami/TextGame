package net.vami.game.world;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.ai.PlayerHandler;
import net.vami.game.interactables.entities.Player;

import javax.sql.ConnectionPoolDataSource;
import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    public static Player player = null;
    public static String playerSavePathFormat = "saves/%.json";
    public static String interactableSavePathFormat = "saves/%_interactables.json";
    public static boolean endGame = false;
    public static boolean isNewGame = true;

    public static void startGame() {
        if (player == null) {
            return;
        }
        if (isNewGame) {
            EnemyHandler.Generate();
            AllyHandler.Generate();
        }

        do {
            ArrayList<Node> tickedNodes = globalTicker();

            for (Node node : tickedNodes) {
                node.getInstance().preTurn();
            }

            for (Node node : tickedNodes) {
                node.getInstance().turn();
            }

        } while (!endGame);

    }

    private static ArrayList<Node> globalTicker() {
        Node playerNode = Node.getNodeFromPosition(player.getPos());
        Node northNode = Node.getNodeFromPosition(player.getPos().add(Direction.NORTH));
        Node southNode = Node.getNodeFromPosition(player.getPos().add(Direction.SOUTH));
        Node eastNode = Node.getNodeFromPosition(player.getPos().add(Direction.EAST));
        Node westNode = Node.getNodeFromPosition(player.getPos().add(Direction.WEST));
        Node upNode = Node.getNodeFromPosition(player.getPos().add(Direction.UP));
        Node downNode = Node.getNodeFromPosition(player.getPos().add(Direction.DOWN));

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(playerNode);
        nodes.add(northNode);
        nodes.add(southNode);
        nodes.add(eastNode);
        nodes.add(westNode);
        nodes.add(upNode);
        nodes.add(downNode);
        return nodes;
    }



    public static Node getCurrentNode() {

        return Node.getNodeFromPosition(player.getPos());
    }

    public static void initializeGame() {
        Node.initializeNodes();
        Action.registerActionSynonyms();
        player = Player.createPlayer();
        if (player == null) return;
        Interactable.spawn(player);
    }

    public static List<Interactable> getInteractables() {
        return Game.getCurrentNode().getInteractables();
    }

}
