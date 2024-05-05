import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public abstract class Game extends GameInitializer {

    private static Scanner playerNameScanner = new Scanner(System.in);
    private static String playerName = playerNameScanner.nextLine();
    public static final Player player = new Player(playerName, 10, 1000, 1, 0, Ability.BURN);
    public static List<Entity> entityList = new ArrayList<>();
    public static List<ObjectInteractable> objectInteractables = new ArrayList<>();
    private static boolean generatedEnemies;
    private static boolean endGame = false;

    public static void StartGame() {
        entityList.add(Game.player);
        while (true) {

            if (!generatedEnemies) {
                    EnemyHandler.Generate();
                    entityDescriber(entityList);
                    generatedEnemies = true;
            }

            EnemyHandler.enemyAction();
            if (Game.player.isAlive()) {
                while (true) {
                    if (PlayerInterpreter.read()) {
                        break;
                    }
                }
            }

            Turn();

            if (endGame) {
                break;
            }
        }

    }

    private static void Turn() {
        Iterator<Entity> entityIterator = entityList.iterator();
        while (entityIterator.hasNext()) {
            Entity nextEntity = entityIterator.next();
            if (nextEntity.isAlive()) {
                nextEntity.entityTurn();
            }
            else {
                System.out.println(nextEntity.getName() + " has died!");
                if (nextEntity.equals(Game.player)) {
                    System.out.println("Game Over!");
                    endGame = true;
                }
                else {
                    entityIterator.remove();
                }
            }
        }
        if (Game.getEnemies().isEmpty()) {
            generatedEnemies = false;
        }
    }

    public static void entityDescriber(List<Entity> entities) {
        for (Entity entity : entities) {
            if (!entity.equals(Game.player)) {
                System.out.println("A " + entity.getName() + " appears before you!" );
            }
        }
    }

    public static void objectDescriber(Object object) {
        System.out.println(object.getDescription());
    }

    public static void nodeDescriber(Node node) {
        System.out.println(node.getDescription());
    }

    public static List<Entity> getEnemies() {
        List<Entity> enemies = new ArrayList<>();
        for (Entity entity : Game.entityList) {
            if (entity.isEnemy()) {
                enemies.add(entity);
            }
        }
        return enemies;
    }

    public static List<Entity> getAllies() {
        List<Entity> allies = new ArrayList<>();
        for (Entity entity : Game.entityList) {
            if (!entity.isEnemy()) {
                allies.add(entity);
            }
        }
        return allies;
    }


}
