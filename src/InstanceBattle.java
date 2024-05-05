import java.util.ArrayList;
import java.util.List;

public class InstanceBattle extends Instance {
    private List<Entity> entities = new ArrayList<>();

    public InstanceBattle() {
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

    public List<Entity> getInstanceAllies() {
        List<Entity> allies = new ArrayList<>();
        for (Entity entity : this.entities) {
            if (!entity.isEnemy()) {
                allies.add(entity);
            }
        }
        return allies;
    }


    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}
