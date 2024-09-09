package net.vami.game.world;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.ai.PlayerHandler;
import net.vami.game.interactables.entities.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    public static Player player = null;
    public static String playerSavePathFormat = "saves/%.json";
    public static boolean endGame = false;


    public static void startGame() {
        if (player == null) {
            return;
        }
        EnemyHandler.Generate();
        AllyHandler.Generate();

        do {
            Node playerNode = Node.getNodeFromPosition(player.getPos());
            Node northNode = Node.getNodeFromPosition(player.getPos().add(Direction.NORTH.pos));
            Node southNode = Node.getNodeFromPosition(player.getPos().add(Direction.SOUTH.pos));
            Node eastNode = Node.getNodeFromPosition(player.getPos().add(Direction.EAST.pos));
            Node westNode = Node.getNodeFromPosition(player.getPos().add(Direction.WEST.pos));

            ArrayList<Node> nodes = new ArrayList<>();
            nodes.add(playerNode);
            nodes.add(northNode);
            nodes.add(southNode);
            nodes.add(eastNode);
            nodes.add(westNode);

            for (Node node : nodes) {
                node.getInstance().preTurn();
            }

            PlayerHandler.read();

            for (Node node : nodes) {
                node.getInstance().turn();
            }

        } while (!endGame);

    }

    static void globalTicker() {

    }



    public static Node getCurrentNode() {

        return Node.getNodeFromPosition(gamePos());
    }

    public static void initializeGame() {
        Node.initializeNodes();
        Action.registerActionSynonyms();
        player = Player.createPlayer();
    }


    public static Position gamePos() {
        return player.getPos();
    }

    public static List<Interactable> getInteractables() {
        return Game.getCurrentNode().getInteractables();
    }

}
