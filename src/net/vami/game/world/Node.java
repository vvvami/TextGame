package net.vami.game.world;

import net.vami.game.Game;
import net.vami.game.interactables.ai.AllyHandler;
import net.vami.game.interactables.ai.EnemyHandler;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.Interactable;

import java.util.*;

public class Node {
    private final Position position;
    private List<UUID> interactables = new ArrayList<>();
    private static HashMap<Position, Node> nodeMap = new HashMap<>();
//    private ArrayList<Direction> entrances = new ArrayList<>();

    public Node(Position position) {
        this.position = position;
        nodeMap.put(position, this);
//        generateEntrances();
    }

    /*
    private void generateEntrances() {
        ArrayList<Direction> entranceList = new ArrayList<>();
        Direction[] directions = Direction.values();
        int entranceNum = new Random().nextInt(1, Direction.values().length);
        for (int i = 0; i < entranceNum; i++) {
            int rnd = new Random().nextInt(Direction.values().length);
            if (entranceList.contains(directions[rnd])) {
                i--;
            } else {
                entranceList.add(directions[rnd]);
            }
        }
        entrances = entranceList;
    }

    public ArrayList<Direction> getEntrances() {
        return entrances;
    }
    */

    public Position getPos() {

        return position;
    }

    public static Node findNode(Position pos) {
        if (pos == null) {
            return null;
        }
        return Node.nodeMap.get(pos);
    }

    public List<Interactable> getInteractables() {
        ArrayList<Interactable> interactableList = new ArrayList<>();
        for (UUID interactable : interactables) {
            interactableList.add(Interactable.getInteractableFromID(interactable));
        }
        return interactableList;
    }

    public boolean hasInteractable(Interactable interactable) {
        return interactables.contains(interactable.getID());
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

    // Ticks every non-enemy and triggers the player input reader
    /* INFO: A "turn" is basically a tick. It's the switch from the player's ability to do an action to the enemy's
       and vice versa. The order in which things tick is important so pay attention if you mess with it. */
    public void turnNoPlayer() {
        if (Game.isEnded()) {return;}
        // We tick the enemy first, as they start
        enemyTicker();
        EnemyHandler.enemyAction(this);

        // Then comes the player and their allies
        if (Game.isEnded()) {return;}
        allyTicker();
        AllyHandler.allyAction(this);
    }

    public void prePlayerTurn() {
        if (Game.isEnded()) {return;}
        // We tick the enemy first, as they start
        enemyTicker();
        EnemyHandler.enemyAction(this);

        // Then comes the player and their allies
        if (Game.isEnded()) {return;}
        allyTicker();
    }

    public void afterPlayerTurn() {

        AllyHandler.allyAction(this);
    }

    // Ticks every enemy
        void enemyTicker() {
            if (Game.isEnded()) {
                return;
            }
            for (Entity enemy : this.getEnemies()) {
                if (!enemy.isEnded()) {
                    enemy.turn();
                }
            }
        }

        // Ticks every non-enemy
        void allyTicker() {
            if (Game.isEnded()) {
                return;
            }
            for (Entity ally : this.getAllies()) {
                if (!ally.isEnded()) {
                   ally.turn();
                }
            }
    }
}
