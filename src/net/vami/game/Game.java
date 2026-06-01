package net.vami.game;

import net.vami.game.display.Display;
import net.vami.game.display.Segmental;
import net.vami.game.display.panel.GameFrame;
import net.vami.game.display.panel.GamePanel;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.ai.PlayerHandler;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.entity.PlayerEntity;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.world.Direction;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.util.Input;
import net.vami.util.InputReceiver;
import net.vami.util.LogUtil;
import net.vami.util.TextUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Game {

    public static PlayerEntity player = null;
    private static Interactable enemyContext;
    private static ItemInstance itemContext;


    public static final String playerSavePathFormat = "saves/%.json";
    public static final String interactableSavePathFormat = "saves/%_interactables.json";

    public static boolean endGame = false;
    public static boolean isNewGame = true;

    private static GameFrame frame;

    public static InputProvider inputProvider;

    public static final int GAME_DELAY = 750;


    public static void startGame() {
        if (player == null) {
            return;
        }

        inputProvider = new InputProvider();
    }

    // This "ticks" every node around the player
    public static ArrayList<Node> getSurroundingNodes() {

        Position position = player.getPos();

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

        protected InputProvider() {
            Input.playerInput.captureInput(this);
            preInput();
        }

        private void preInput() {
//            for (Node node : getSurroundingNodes()) {
//                node.turnNoPlayer();
//            }
            Game.getCurrentNode().prePlayerTurn();
        }

        @Override
        public void receiveInput(String input) {
            Node prevPlayerNode = Game.getCurrentNode();

            if (prevPlayerNode == null) {
                return;
            }

            input = input.stripLeading(); // eliminates space leading to the actual input
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
            Display.print("Enter your name, traveler: %n");
            Input.playerInput.captureInput(this);
        }
        @Override
        public void receiveInput(String input) {
            input = input.stripLeading();
            player = PlayerEntity.createPlayer(input);
            if (player != null){
                Input.playerInput.releaseInput(this);
                PlayerEntity.spawnInteractable(player);
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
        LogUtil.log("Game initialized");
    }

    public static boolean isEnded() {
        if (Game.endGame) {
            return true;
        }
        if (Game.player.isEnded()) {
            Display.showText("Game Over! %n");
            Game.endGame = true;
        }
        return Game.endGame;
    }

    public static List<Interactable> getInteractables() {

        return Game.getCurrentNode().getInteractables();
    }

    public static GamePanel getPanel() {
        return frame.getPanel();
    }

    public static GameFrame getFrame() {
        return frame;
    }

    public static void enablePlayerInput(boolean enable) {
        frame.getPanel().getPlayerInput().setEditable(enable);
    }

    public static void setEnemyContext(Interactable interactable) {
        enemyContext = interactable;
    }

    public static Interactable getEnemyContext() {
        return enemyContext;
    }

    public static void setItemContext(ItemInstance item) {
        itemContext = item;
    }

    public static ItemInstance getItemContext() {
        return itemContext;
    }


}
