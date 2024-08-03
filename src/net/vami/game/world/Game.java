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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Game {
    public static final Player player = new Player(namePlayer(),
            new Entity.Attributes()
                    .level(2)
                    .ability(new HypnosisAbility()));

    public static boolean endGame = false;


    public static void startGame() {

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



    public static List<Interactable> getInteractables() {
        return Game.getCurrentNode().getInteractables();
    }

}
