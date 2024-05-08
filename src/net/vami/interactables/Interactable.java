package net.vami.interactables;
import net.vami.game.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class Interactable {
    private boolean equipped;
    private final UUID ID;
    private final String name;
    private Position position;
    private String description;
    private List<Action> receivableActions = new ArrayList<>();
    private List<Action> availableActions = new ArrayList<>();
    private Interactable ended;

    public Interactable getEnded() {
        return ended;
    }

    public void setEnded(Interactable ended) {
        this.ended = ended;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Interactable(String name, String description, Position position) {
        this.name = name;
        this.description = description;
        this.position = position;
        ID = UUID.randomUUID();

    }

    public boolean isEnded() {
        return false;
    }

    public UUID getID() {
        return ID;
    }

    public boolean receiveAction(Interactable source, Action action) {
        switch (action) {
            case Action.ATTACK: return receiveAttack(source, action);
            case Action.MOVEMENT:
            case Action.USE:
            case Action.TAKE:
            case Action.EQUIP:
            case Action.ABILITY:


        }
        return true;
    }

    public boolean applyAction(Interactable target, Action action) {
        return false;
    }

    public void kill() {
        setPosition(null);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        if (position == this.position) {
            return;
        }
        Node.getNodeFromPosition(this.position).removeInteractable(this);
        this.position = position;
        Node node = Node.getNodeFromPosition(this.position);
        if (node != null) {
            node.addInteractable(this);
        }

    }

    protected boolean receiveAttack(Interactable source, Action action) {
        if (!receivableActions.contains(action)) {
            return false;
        }

        Interactable ended = this.ended == null
                ? new InteractableEnded(name, description, position)
                : this.ended;
        Node node = Node.getNodeFromPosition(this.position);
        node.removeInteractable(this);
        node.addInteractable(ended);

        return true;
    }
}
