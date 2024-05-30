package net.vami.interactables;
import net.vami.game.*;
import net.vami.interactables.entities.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Interactable {
    private boolean equipped;
    private final UUID ID;
    private final String name;
    private Position position;
    private String description;
    private Set<Action> receivableActions = new HashSet<>();
    private Set<Action> availableActions = new HashSet<>();
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
        if (!receivableActions.contains(action)) {
            System.out.println("Nothing happens.");
            return false;
        }

        return switch (action) {
            case Action.ATTACK -> receiveAttack(source);
            case Action.MOVEMENT -> receiveMovement(source);
            case Action.TAKE -> receiveTake(source);
            case Action.EQUIP -> receiveEquip(source);
            case Action.ABILITY -> receiveAbility(source);
            case Action.SAVE -> receiveSave(source);
            default -> false;
        };
    }

    public boolean applyAction(Interactable target, Action action) {
        if (!availableActions.contains(action)) {
            System.out.printf("%s tries to %s %s, but nothing happens. %n",
                    getName(), action.getSynonyms().stream().findAny(), target.getName());
            return false;
        }

        return switch (action) {
            case Action.ATTACK -> target.receiveAttack(this);
            case Action.MOVEMENT -> target.receiveMovement(this);
            case Action.TAKE -> target.receiveTake(this);
            case Action.EQUIP -> target.receiveEquip(this);
            case Action.ABILITY -> target.receiveAbility(this);
            case Action.SAVE -> target.receiveSave(this);
            default -> false;
        };
    }



    public void kill() {
        position = null;
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

    public void addReceivableAction(Action action) {
        receivableActions.add(action);
    }

    public void addAvailableAction(Action action) {
        availableActions.add(action);
    }

    public void removeReceivableAction(Action action) {
        receivableActions.remove(action);
    }

    public void removeAvailableAction(Action action) {
        availableActions.remove(action);
    }

    protected boolean receiveAttack(Interactable source) {
        Interactable ended = this.ended == null
                ? new InteractableEnded(name, description, position)
                : this.ended;
        Node node = Node.getNodeFromPosition(this.position);
        node.removeInteractable(this);
        node.addInteractable(ended);
        return true;
    }

    protected boolean receiveAbility(Interactable source) {
        Interactable ended = this.ended == null
                ? new InteractableEnded(name, description, position)
                : this.ended;
        Node node = Node.getNodeFromPosition(this.position);
        node.removeInteractable(this);
        node.addInteractable(ended);
        return true;
    }

    protected boolean receiveEquip(Interactable source) {
        return false;
    }

    protected boolean receiveMovement(Interactable source) {
        return false;
    }

    protected boolean receiveTake(Interactable source) {
        return false;
    }

    protected boolean receiveSave(Interactable source) {
        return false;
    }

    public void addStatus(@NotNull StatusInstance status) {

    }

    public void heal(Entity source, float amount) {

    }

    public boolean useAbility(Interactable target) {
        return false;
    }

}
