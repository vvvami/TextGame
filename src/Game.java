import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public abstract class Game extends GameInitializer {

    private static Scanner playerNameScanner = new Scanner(System.in);
    private static String playerName = playerNameScanner.nextLine();
    public static final Player player = new Player(playerName, new Position(0,0,0), 1, 1000, 1, 0, Ability.HEAL);
    private static boolean endGame = false;

    public static void StartGame() {
        EnemyHandler.Generate(player.getPosition());

        do {

            EnemyHandler.enemyAction();

            if (Game.player.isAlive()) {
                PlayerInterpreter.read();
            }

            Turn();
        }
        while (!endGame);

    }

    private static void Turn() {
        List<Entity> entities = Node.getEntities();
        for (Entity entity : entities) {
            if (entity.isAlive()) {
                entity.entityTurn();
            }
            else {
                System.out.println(entity.getName() + " has died!");
                if (entity.equals(Game.player)) {
                    System.out.println("Game Over!");
                    endGame = true;
                }
                else {
                    getCurrentNode().removeInteractable(entity);
                }
            }
        }
        if (Node.getEnemies().isEmpty()) {
            endGame = true;
        }
    }


    public static Node getCurrentNode() {
        return Node.getNodeFromPosition(player.getPosition());
    }


}
