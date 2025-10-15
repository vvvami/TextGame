package net.vami.game;

import net.vami.TextGame;
import net.vami.game.display.panels.GameFrame;
import net.vami.game.display.panels.custom.GamePanel;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.ai.PlayerHandler;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.entities.PlayerEntity;
import net.vami.game.interactables.items.Item;
import net.vami.game.world.Direction;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.util.Input;
import net.vami.util.InputReceiver;
import net.vami.util.LogUtil;
import net.vami.util.TextUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Game {

    public static PlayerEntity player = null;
    private static Position lastPlayerPos;

    public static String playerSavePathFormat = "saves/%.json";
    public static String interactableSavePathFormat = "saves/%_interactables.json";

    public static boolean endGame = false;
    public static boolean isNewGame = true;

    private static GameFrame frame;

    public static InputProvider inputProvider;

    public static void startGame() {
        if (player == null) {
            return;
        }
        if (isNewGame) {
            EnemyHandler.Generate();
            AllyHandler.Generate();
        }

        inputProvider = new InputProvider();
    }

    // This "ticks" every node around the player
    public static ArrayList<Node> getSurroundingNodes() {

        Position position = player.getPos() != null ? player.getPos() : lastPlayerPos;

        ArrayList<Node> nodes = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Node node = Node.findNode(position.add(direction));
            if (node == null) {
                continue;
            }
            nodes.add(node);
        }

        return nodes;
    }

    public static class InputProvider implements InputReceiver {
//        private Semaphore inputAvailable = new Semaphore(0);
//        private Queue<String> inputList = new ConcurrentLinkedQueue<>();

        protected InputProvider() {
            Input.playerInput.captureInput(this);
            preInput();

        }

        private void preInput() {
            for (Node node : getSurroundingNodes()) {
                node.turnNoPlayer();
            }
            Game.getCurrentNode().prePlayerTurn();
        }

        @Override
        public void receiveInput(String input) {
             Node prevPlayerNode = Game.getCurrentNode();

             if (prevPlayerNode == null) {return;}

             PlayerHandler.inputToAction(input);
             prevPlayerNode.afterPlayerTurn();
             preInput();
        }
    }


    public static Node getCurrentNode() {

        return Node.findNode(player.getPos());
    }

    private static class PlayerCreator implements InputReceiver {
        protected PlayerCreator() {
            Game.display("Enter your name, traveler: %n");
            Input.playerInput.captureInput(this);
        }
        @Override
        public void receiveInput(String input) {
            player = PlayerEntity.createPlayer(input);
            if (player != null){
                Input.playerInput.releaseInput(this);
                PlayerEntity.spawnInteractable(player);
                LogUtil.Log("Player pos: %s", player.getPos());
                Game.startGame();
            }
        }
    }

    private static PlayerCreator playerCreator;

    public static void initializeGame() {
        frame = new GameFrame();

        Node.initializeNodes();
        TextUtil.registerColorMap();
        Action.registerActionSynonyms();

        enablePlayerInput(true);
        playerCreator = new PlayerCreator();
        LogUtil.Log("Game initialized");
    }

    public static boolean isEnded() {
        if (Game.endGame) {
            return true;
        }
        if (Game.player.isEnded()) {
            Game.display("Game Over! %n");
            Game.endGame = true;
        }
        return Game.endGame;
    }

    public static List<Interactable> getInteractables() {

        return Game.getCurrentNode().getInteractables();
    }

    public static void playSound(Interactable source, Sound sound, int volume) {
        playSound(source.getPos(), sound, volume);
    }

    public static void playSound(Position position, Sound sound, int volume) {
        sound.playSound(position, volume);
    }

    public static void playMusic(Sound sound, int volume) {
        sound.playMusic(volume);
    }

    public static void display(Interactable source, Color color, String text, Object ... args) {
        TextUtil.display(source, color, text, args);
    }

    public static void display(Interactable source, String text, Object ... args) {
        display(source, TextUtil.defaultTextColor, text, args);
    }

    public static void display(String text, Color color, Object ... args) {
        display(null, color, text, args);

    }

    public static void display(String text, Object ... args) {
        display(text, TextUtil.defaultTextColor, args);
    }

    public static GamePanel getDisplay() {
        return frame.getPanel();
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void enablePlayerInput(boolean enable) {
        frame.getPanel().getPlayerInput().setEditable(enable);
    }

}
