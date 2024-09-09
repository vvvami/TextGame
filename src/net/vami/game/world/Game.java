package net.vami.game.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.ai.PlayerHandler;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Player;
import net.vami.game.interactables.interactions.damagetypes.BluntDamage;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Game {
    public static String playerSavePath = "saves/save.json";

    public static Player player = new Player(namePlayer(), new Entity.Attributes().level(4));

    public static boolean endGame = false;


    public static void startGame() {
        Player temp = player;
        loadSave();
        if (!temp.getName().equals(player.getName())) {
           player = temp;
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

    public static void saveGame() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        File saveFile = new File(Game.playerSavePath);

        try (FileWriter saveWriter = new FileWriter(Game.playerSavePath)) {
            gson.toJson(player, saveWriter);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadSave() {
        File saveFile = new File(Game.playerSavePath);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        if (saveFile.exists()) {
            FileReader reader = null;
            try {
                reader = new FileReader(Game.playerSavePath);
                player = gson.fromJson(reader, Player.class);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
