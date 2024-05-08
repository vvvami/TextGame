public class EnemyHandler {

    public static void enemyAction() {

    }

    public static void enemyTargeting(Entity source) {
        if (source.getTarget() == null || !source.getTarget().isAlive()) {
            for (Entity ally : Node.getAllies()) {
                source.setTarget(ally);
            }
        }
    }

    public static void Generate(Position position) {
        int randonNum = Math.min((int) (Math.random() * Math.random() * 100), 4);
        Entity enemy;
        switch (randonNum) {
            case 1: enemy = new Maneater(15);
            case 2: enemy = new Werewolf(50);
            case 3: enemy = new Werewolf(3);
            default: enemy = new Maneater(5);
        }
        Game.getCurrentNode().getInteractables().add(enemy);
    }

}
