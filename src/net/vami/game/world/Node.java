package net.vami.game.world;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.Interactable;

import java.util.*;

public class Node {
    private String description;
    private final Position position;
    private List<Interactable> interactables = new ArrayList<>();
    private static HashMap<Position, Node> nodeMap = new HashMap<>();


    public Node(Position position) {
        this.position = position;
        nodeMap.put(position, this);
    }


    public Position getNodePos() {
        return position;
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
        interactable.kill();
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

    public static List<Entity> getEnemies() {
        List<Entity> enemies = new ArrayList<>();
        for (Entity entity : Node.getEntities()) {
            if (entity.isEnemy()) {
                enemies.add(entity);
            }
        }
        return enemies;
    }

    public static List<Entity> getAllies() {
        List<Entity> allies = new ArrayList<>();
        for (Entity entity : Node.getEntities()) {
            if (!entity.isEnemy()) {
                allies.add(entity);
            }
        }
        return allies;
    }

    public static List<Entity> getEntities() {
        return ((Game.getCurrentNode()
                .getInteractables().stream().filter(interactable -> interactable instanceof Entity)
                .map(interactable -> (Entity) interactable))).toList();
    }

    public static void initializeNodes() {
        int size = 50;
            for (int j = -size; j <= size; j++) {
                for (int i = -size; i <= size; i++) {
                new Node(new Position(i, 0, j));
                }
            }

    }


}
