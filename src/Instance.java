import java.util.ArrayList;
import java.util.List;

public class Instance {
    private List<Entity> entities = new ArrayList<>();
    private Player player;

    public Instance(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getInstanceEntities() {
        return entities;
    }

    public List<Entity> getInstanceEnemies() {
        List<Entity> enemies = new ArrayList<>();
        for (Entity entity : this.entities) {
            if (entity.isEnemy()) {
                enemies.add(entity);
            }
        }
        return enemies;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public Player getInstancePlayer() {
        return player;
    }
}
