package net.vami.game;

import net.vami.game.display.panels.GameFrame;
import net.vami.game.display.panels.custom.GamePanel;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.ai.PlayerHandler;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.entities.Player;
import net.vami.game.interactables.items.Item;
import net.vami.game.world.Direction;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.util.Input;
import net.vami.util.InputReceiver;
import net.vami.util.TextUtil;
import org.fusesource.jansi.AnsiConsole;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Game {

    public static Player player = null;
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
            Node node = Node.getNodeFromPosition(position.add(direction));
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
             PlayerHandler.inputToAction(input);
             prevPlayerNode.afterPlayerTurn();

             preInput();
        }
    }


    public static Node getCurrentNode() {

        return Node.getNodeFromPosition(player.getPos());
    }

    private static class PlayerCreator implements InputReceiver {
        protected PlayerCreator() {
            TextUtil.display((Interactable) null, "Enter your name, traveler: %n");
            Input.playerInput.captureInput(this);
        }
        @Override
        public void receiveInput(String input) {
            player = Player.createPlayer(input);
            if (player != null){
                Input.playerInput.releaseInput(this);
                Interactable.spawn(player);
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
    }

    public static boolean isEnded() {
        if (Game.endGame) {
            return true;
        }
        if (Game.player.isEnded()) {
            TextUtil.display((Interactable) null,"Game Over! %n");
            Game.endGame = true;
        }
        return Game.endGame;
    }

    public static List<Interactable> getInteractables() {

        return Game.getCurrentNode().getInteractables();
    }

    public static void playSound(Interactable source, Sound sound, int volume) {
        if (!Sound.hasAvailableAudioOutput() || sound == null) {
            return;
        }

        if (source instanceof Item ||
                source.getNode() == Game.getCurrentNode()) {
            sound.play(volume);
        }
    }

    public static void playMusic(Sound sound, int volume) {
        if (!Sound.hasAvailableAudioOutput() || sound == null) {
            return;
        }

        for (Sound sound1 : Sound.getSounds()) {
                if (sound1.getClip() != null &&
                        sound1.isPlaying() &&
                        sound1.getSoundType().equals(sound.getSoundType())) {
                    sound1.stop();
            }
        }
            sound.play(volume);
            sound.loop();
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
