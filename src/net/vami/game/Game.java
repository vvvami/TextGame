package net.vami.game;

import net.vami.game.display.sound.Sound;
import net.vami.game.display.sound.SoundType;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.entities.Player;
import net.vami.game.world.Direction;
import net.vami.game.world.Node;
import org.fusesource.jansi.AnsiConsole;

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

            for (Node node : globalTicker()) {
                node.getInstance().preTurn();
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
        AnsiConsole.systemInstall();
        player = Player.createPlayer();
        if (player == null) return;
        Interactable.spawn(player);
    }

    public static List<Interactable> getInteractables() {

        return Game.getCurrentNode().getInteractables();
    }

    public static void playSound(Sound sound, int volume) {
        if (sound == null) {
            return;
        }
        sound.play(volume);
    }

    public static void playMusic(Sound sound, int volume) {
        if (sound == null) {
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

}
