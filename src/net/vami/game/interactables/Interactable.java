package net.vami.game.interactables;
import net.vami.game.interactables.interactions.*;
import net.vami.game.world.Direction;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.interactions.statuses.CrippledStatus;
import net.vami.game.interactables.interactions.statuses.FrozenStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Interactable {
    private final UUID ID;
    private String name;
    private Position position = new Position(0,0,0);
    private String description;
    private Set<Action> receivableActions = new HashSet<>();
    private Set<Action> availableActions = new HashSet<>();
    private Interactable ended;
    private Direction direction;

    private List<Modifier> modifiers = new ArrayList<>();

    public Interactable(String name) {
        this.name = name;

        if (Node.getNodeFromPosition(position) != null) {
            Node.getNodeFromPosition(position).addInteractable(this);
        }

        ID = UUID.randomUUID();

    }

    public boolean isEnded() {

        return this == ended;
    }

    public UUID getID() {

        return ID;
    }

    public Interactable getEnded() {

        return ended;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public void setName(String name) {

        this.name = name;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public boolean receiveAction(Interactable source, Action action) {
        if (!actionPredicate(source)) {
            return false;
        }

        if (!source.availableActions.contains(action)) {
            System.out.printf("%s tries to %s %s, but nothing happens. %n",
                    source.getName(), action.getSynonyms().getFirst(), this.getName());
            return false;
        }

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
            case Action.RESIST -> receiveResist(source);
            case Action.DROP -> receiveDrop(source);
            default -> false;
        };
    }

    private boolean actionPredicate(Interactable source) {
        Entity sourceEntity = (Entity) source;

        // can simplify this into a switch if needed in the future
        if (sourceEntity.hasSpecifiedStatus(new CrippledStatus()) &&
                (Math.random() < (double) sourceEntity.getStatusInstance(new CrippledStatus()).getAmplifier() / 10)) {
            return false;
        }

        if (sourceEntity.hasSpecifiedStatus(new FrozenStatus()) &&
                (Math.random() < (double) sourceEntity.getStatusInstance(new FrozenStatus()).getAmplifier() / 20)) {
            return false;
        }

        return true;
    }

    public void kill() {
        if (Node.getNodeFromPosition(this.position).getInteractables().contains(this)) {
            Node.getNodeFromPosition(this.position).removeInteractable(this);
        }

        position = null;
    }

    public Position getPos() {

        return position;
    }

    public void setPosition(Position position) {
        if (position.equals(this.position)) {
            return;
        }

        if (this.position != null && Node.getNodeFromPosition(this.position).getInteractables().contains(this)) {
            Node.getNodeFromPosition(this.position).removeInteractable(this);
        }

        this.position = position;
        Node node = Node.getNodeFromPosition(this.position);
        if (node != null) {
            node.addInteractable(this);
        }
    }

    public Node getNode() {
        return Node.getNodeFromPosition(position);
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


    public boolean receiveAttack(Interactable source) {
        Interactable ended = this.ended == null
                ? new InteractableEnded(name)
                : this.ended;
        Node node = Node.getNodeFromPosition(this.position);
        node.removeInteractable(this);
        node.addInteractable(ended);
        return true;
    }

    public boolean receiveAbility(Interactable source) {
        Interactable ended = this.ended == null
                ? new InteractableEnded(name)
                : this.ended;
        Node node = Node.getNodeFromPosition(this.position);
        node.removeInteractable(this);
        node.addInteractable(ended);
        return true;
    }

    public boolean receiveEquip(Interactable source) {

        return false;
    }

    public boolean receiveMovement(Interactable source) {
        Position newPos = position.add(direction.pos);

        if (Node.getNodeFromPosition(newPos) == null) {
            return false;
        }

        this.setPosition(newPos);
        System.out.println(this.getName() + " has moved to position: " + this.position.toString());
        return true;
    }


    public boolean receiveTake(Interactable source) {

        return false;
    }

    public boolean receiveSave(Interactable source) {

        return false;
    }

    public boolean receiveResist(Interactable source) {

        return false;
    }

    public boolean receiveDrop(Interactable source) {

        return false;
    }

    public void addStatus(@NotNull Status.Instance status) {

    }

    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
    }

    public void removeModifier(String ID) {
        if (modifiers.isEmpty()) {
            return;
        }

        Modifier removeMod = modifiers.getFirst();
        for (Modifier modifier : modifiers) {
            if (Objects.equals(modifier.getID(), ID)) {
                removeMod = modifier;
                break;
            }
        }
        modifiers.remove(removeMod);
    }

    public boolean hasModifier(String ID) {
        for (Modifier modifier : modifiers) {
            if (ID.equals(modifier.getID())) {
                return true;
            }
        }
        return false;
    }


    public void hurt(Entity source, float amount, DamageType damageType) {

    }

    public void heal(Entity source, float amount) {

    }

}
