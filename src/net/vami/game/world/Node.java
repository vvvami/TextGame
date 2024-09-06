package net.vami.game.world;

import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.items.ItemEquipable;

import java.util.*;

public class Node {
    private String description;
    private final Position position;
    private List<Interactable> interactables = new ArrayList<>();
    private static HashMap<Position, Node> nodeMap = new HashMap<>();
    private Node.Instance instance;

    public Node(Position position) {
        this.position = position;
        nodeMap.put(position, this);
        this.instance = new Instance(this);
    }

    public Position getNodePos() {

        return position;
    }

    public Node.Instance getInstance() {
        return instance;
    }

    public static Node getNodeFromPosition(Position pos) {
        return Node.nodeMap.get(pos);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<Interactable> getInteractables() {
        return interactables;
    }

    public void addInteractable(Interactable interactable) {
        interactables.add(interactable);
    }

    public void removeInteractable(Interactable interactable) {
        interactables.remove(interactable);
    }

    public Interactable stringToInteractable(String name) {
        if (!interactables.isEmpty()) {
            for (Interactable interactable : interactables) {
                if (interactable == null) {
                    break;
                }
                if (name.equalsIgnoreCase(interactable.getName())) {
                    return interactable;
                }
            }
        }
        return null;
    }

    public List<Entity> getEnemies() {
        List<Entity> enemies = new ArrayList<>();
        for (Entity entity : this.getEntities()) {
            if (entity.isEnemy()) {
                enemies.add(entity);
            }
        }
        return enemies;
    }

    public List<Entity> getAllies() {
        List<Entity> allies = new ArrayList<>();
        for (Entity entity : this.getEntities()) {
            if (!entity.isEnemy()) {
                allies.add(entity);
            }
        }
        return allies;
    }

    public List<Entity> getEntities() {
        return ((this.getInteractables().stream()
                .filter(interactable -> interactable instanceof Entity)
                .map(interactable -> (Entity) interactable))).toList();
    }

    public static void initializeNodes() {
        int size = 50;
        for (int h = -size; h <= size; h++) {
            for (int j = -size; j <= size; j++) {
                for (int i = -size; i <= size; i++) {
                    new Node(new Position(i, h, j));
                }
            }
        }
    }

    public static HashMap<Position, Node> getNodeMap() {
        return nodeMap;
    }

    public static class Instance {
        private Node node;

        Instance(Node node) {
            this.node = node;
        }

        public void preTurn() {
            itemTicker();
            EnemyHandler.enemyAction(node);
            allyTicker();
        }

        public void turn() {
            AllyHandler.allyAction(node);
            enemyTicker();
        }

        void itemTicker() {
            List<Entity> entities = node.getEntities();
            for (Entity entity : entities) {
                List<ItemEquipable> itemEquipables = entity.getEquippedItems();

                for (ItemEquipable item : itemEquipables)
                {
                    item.turn();
                }
                if (entity.getHeldItem() != null) {
                    entity.getHeldItem().turn();
                }
            }
        }

        void enemyTicker() {
            for (Entity enemy : node.getEnemies()) {
                if (!enemyEndedCheck(enemy)) {
                    enemy.turn();
                }
            }

            for (Entity ally : node.getAllies()) {
                allyEndedCheck(ally);
            }
        }

        void allyTicker() {
            for (Entity ally : node.getAllies()) {
                if (allyEndedCheck(ally)) {
                    if (Game.endGame) {
                        return;
                    }
                }
                else {
                    ally.turn();
                }
            }

            for (Entity enemy : node.getEnemies()) {
                enemyEndedCheck(enemy);
            }
        }

        private boolean allyEndedCheck(Entity ally) {
            if (ally.isEnded()) {
                System.out.println(ally.getName() + " has died!");
                if (ally.equals(Game.player)) {
                    System.out.println("Game Over!");
                    Game.endGame = true;
                }
                ally.kill();
                return true;
            }
            return false;
        }

        private boolean enemyEndedCheck(Entity enemy) {
            if (enemy.isEnded()) {
                System.out.println(enemy.getName() + " has died!");
                enemy.kill();
                return true;
            }
            return false;
        }
    }
}
