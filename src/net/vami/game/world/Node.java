package net.vami.game.world;

import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.ai.PlayerHandler;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.items.equipables.ItemEquipable;

import java.util.*;

public class Node {
    private String description;
    private final Position position;
    private List<UUID> interactables = new ArrayList<>();
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
        if (pos == null) {
            return null;
        }
        return Node.nodeMap.get(pos);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<Interactable> getInteractables() {
        ArrayList<Interactable> interactableList = new ArrayList<>();
        for (UUID interactable : interactables) {
            interactableList.add(Interactable.getInteractableFromID(interactable));
        }
        return interactableList;
    }

    public void addInteractable(Interactable interactable) {
        interactables.add(interactable.getID());
    }

    public void removeInteractable(Interactable interactable) {
        interactables.remove(interactable.getID());
    }

    public Interactable stringToInteractable(String name) {
        if (!getInteractables().isEmpty()) {
            for (Interactable interactable : getInteractables()) {
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
        int size = 25;
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

        // Ticks every equipped item of entities within the node as well as the enemies themselves
        public void preTurn() {
            itemTicker();
            if (enemyTicker()) {
                return;
            }
            EnemyHandler.enemyAction(node);
        }

        // Turn ticks every non-enemy and triggers the player input reader
        public void turn() {
            if (allyTicker()) {
               return;
            }

            if (Game.player.getPos().equals(node.getNodePos())) {
                PlayerHandler.read();
            }
            AllyHandler.allyAction(node);
        }

        // Ticks every item
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

        // Ticks every enemy
        boolean enemyTicker() {
            if (entityEndedCheck()) {
                return true;
            }
            for (Entity enemy : node.getEnemies()) {
                if (!enemy.isEnded()) {
                    enemy.turn();
                }
            }
            return false;
        }

        // Ticks every non-enemy
        boolean allyTicker() {
            if (entityEndedCheck()) {
                return true;
            }
            for (Entity ally : node.getAllies()) {
                if (!ally.isEnded()) {
                   ally.turn();
                }
            }
            return false;
        }

        // Checks for every dead entity within a node and removes them, or ends the game if it's the player that died
        private boolean entityEndedCheck() {
            if (Game.endGame) {
                return true;
            }
            for (Entity entity : node.getEntities()) {
                if (entity.isEnded()) {
                    System.out.println(entity.getName() + " has died!");
                    if (entity.equals(Game.player)) {
                        System.out.println("Game Over!");
                        Game.endGame = true;
                        return true;
                    }
                    // We use remove() and not annihilate()
                    // Reason: status instances entities inflict may outlast themselves (we still need their UUID)
                    entity.remove();
                }
            }
            return false;
        }
    }
}
