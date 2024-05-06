import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public abstract class Game extends GameInitializer {

    private static Scanner playerNameScanner = new Scanner(System.in);
    private static String playerName = playerNameScanner.nextLine();
    public static final Player player = new Player(playerName, 1, 1000, 1, 0, Ability.HEAL);
    private static boolean endGame = false;

    public static void StartGame() {
        player.setPosition(new Position(0,0,0));
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
        List<Entity> entityStream = ((Game.getCurrentNode()
                .getInteractables().stream().filter(interactable -> interactable instanceof Entity)
                .map(interactable -> (Entity) interactable))).toList();
        for (Entity entity : entityStream) {
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
        if (Game.getEnemies().isEmpty()) {
            endGame = true;
        }
    }


    public static List<Entity> getEnemies() {
        List<Entity> enemies = new ArrayList<>();
        for (Entity entity : Game.interactables) {
            if (entity.isEnemy()) {
                enemies.add(entity);
            }
        }
        return enemies;
    }

    public static List<Entity> getAllies() {
        List<Entity> allies = new ArrayList<>();
        for (Entity entity : Game.interactables) {
            if (!entity.isEnemy()) {
                allies.add(entity);
            }
        }
        return allies;
    }

    public static Node getCurrentNode() {
        return Node.getNodeFromPosition(player.getPosition());
    }


}
