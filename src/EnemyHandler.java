import java.util.List;
import java.util.Random;

public class EnemyHandler {

    public static void enemyAction() {
        for (Entity enemy : Game.getEnemies()) {
            enemyTargeting(enemy);
            if (Math.random() < 0.75) {
                enemy.setAction(Action.attack);
            }
            else {
                enemy.setAction(Action.ability);
            }
            Action.entityAction(enemy.getTarget(), enemy);
        }
    }

    public static void enemyTargeting(Entity source) {
        if (source.getTarget() == null || !source.getTarget().isAlive()) {
            for (Entity ally : Game.getAllies()) {
                source.setTarget(ally);
            }
        }
    }

    public static void Generate() {
        int randonNum = Math.min((int) (Math.random() * Math.random() * 100), 4);
        Entity enemy;
        switch (randonNum) {
            case 1: enemy = new Maneater(15);
            case 2: enemy = new Werewolf(50);
            case 3: enemy = new Werewolf(3);
            default: enemy = new Maneater(5);
        }
        Game.entityList.add(enemy);
    }

}
