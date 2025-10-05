package net.vami.game.interactables;
import com.google.gson.*;
import net.vami.game.interactables.entities.PlayerEntity;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.interactions.modifier.Modifier;
import net.vami.game.interactables.items.Item;
import net.vami.game.world.Direction;
import net.vami.game.Game;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.util.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Interactable {
    private final UUID ID;
    private String name;
    private Position position = null;
    private String description;
    private Set<Action> receivableActions = new HashSet<>();
    private Set<Action> availableActions = new HashSet<>();

    private List<Status.Instance> statusEffects = new ArrayList<>();
    private List<Status> immunities = new ArrayList<>();

//    private Interactable ended;
    private Direction direction;
    private static HashMap<UUID, Interactable> interactableMap = new HashMap<>();

    private List<Modifier> modifiers = new ArrayList<>();


    public Interactable(String name) {
        ID = UUID.randomUUID();
        interactableMap.put(ID, this);

        this.name = name;

    }

    public void turn() {
        if (!getStatuses().isEmpty()) {
            statusTurn();
        }
    }

    // Spawns an interactable with a defined position
    public static void spawn(Interactable interactable, Position position) {
        if (interactable instanceof Entity entity
                && entity.isEnded()) {
            LogUtil.Log(LoggerType.ERROR,
                    "Cannot spawn dead entity: %s",
                    interactable.getDisplayName());
            return;
        }

        if (position == null) {
            LogUtil.Log(LoggerType.ERROR,
                    "Cannot spawn interactable: Position is null! %s",
                    interactable);
            return;
        }
        Node nodeNew = Node.findNode(position);
        if (nodeNew == null) {
            LogUtil.Log(LoggerType.ERROR,
                    "Cannot spawn interactable: Node does not exist at position! %s",
                    interactable);
            return;
        }

        if (!nodeNew.hasInteractable(interactable)) {
            nodeNew.addInteractable(interactable);
        }

        if (!interactableMap.containsKey(interactable.ID)) {
            addToMap(interactable);
        }

        interactable.setPos(position);
    }

    // Spawns the entity at its default set position. If the position is null, it will spawn at the player's position
    public static void spawn(Interactable interactable) {
        if (interactable instanceof Entity entity
                && entity.isEnded()) {
            LogUtil.Log(LoggerType.ERROR,
                    "Cannot spawn dead entity: %s",
                    interactable.getDisplayName());
            return;
        }

        Position newPos = interactable.position;
        if (newPos == null) {
            if (Game.player != null && Game.player.getPos() != null) {
                newPos = Game.player.getPos();
            } else {
                LogUtil.Log(LoggerType.WARN,
                        "Interactable position is null: using default (%s)", interactable);
                newPos = new Position(0,0,0);
            }
        }
        spawn(interactable, newPos);
    }

    // Saves all interactables specific to a player
    public static void saveInteractables(PlayerEntity player) {
        String saveFilePath = Game.interactableSavePathFormat.replace("%", HexUtil.toHex(player.getName()));
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonArray interactableArray = new JsonArray();
        for (Interactable interactable : interactableMap.values()) {
            if (interactable.getClass().equals(PlayerEntity.class)) {
                continue;
            }
            interactableArray.add(serializeInteractable(interactable, gson));
        }

        JsonObject mainObj = new JsonObject();
        mainObj.add("interactables", interactableArray);


        try (FileWriter saveWriter = new FileWriter(saveFilePath)) {
            saveWriter.write(gson.toJson(mainObj));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonElement serializeInteractable(Object o, Gson gson) {
        JsonObject classObj = new JsonObject();
        classObj.addProperty("klass", o.getClass().getName());
        classObj.add("value", gson.toJsonTree(o));
        return classObj;
    }

    public static Interactable deserializeInteractable(JsonElement jsonElement, Gson gson) throws JsonParseException {
        JsonObject mainObj = jsonElement.getAsJsonObject();
        String fullName = mainObj.get("klass").getAsString();
        Class klass = ClassUtil.getObjectClass(fullName);
        return (Interactable) gson.fromJson(mainObj.get("value"), klass);
    }

    public static void loadInteractables(String playerName) {
        String saveFilePath = Game.interactableSavePathFormat.replace("%", HexUtil.toHex(playerName));
        File saveFile = new File(saveFilePath);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        JsonArray mainArr;
        JsonObject mainObj;

        if (saveFile.exists()) {
            FileReader reader = null;
            try {
                reader = new FileReader(saveFile);
                mainObj = gson.fromJson(reader, JsonObject.class);
                mainArr = mainObj.getAsJsonArray("interactables").getAsJsonArray();
                for (JsonElement element : mainArr) {
                    Interactable interactable = deserializeInteractable(element, gson);
                    addToMap(interactable);
                    Interactable.spawn(interactable);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            Game.isNewGame = false;
        }
    }

    public static Interactable getInteractableFromID(UUID ID) {
        return interactableMap.get(ID);
    }

    public static void addToMap(Interactable interactable) {

        interactableMap.put(interactable.ID, interactable);
    }

    public static HashMap<UUID, Interactable> getInteractableMap() {
        return interactableMap;
    }

    public boolean isEnded() {

//        return this == ended;
        return false;
    }

    public UUID getID() {

        return ID;
    }

//    public Interactable getEnded() {
//
//        return ended;
//    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

//    public void setEnded(Interactable ended) {
//
//        this.ended = ended;
//    }

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

    public String getDisplayName() {
        return getName();
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public boolean receiveAction(Interactable source, Action action) {
        if (!actionPredicate(source)) {
            return false;
        }

        String targetName = this == source ? "" : " " + this.getName();

        if (!source.availableActions.contains(action)) {
            TextUtil.display(this,"%s tries to %s %s, but nothing happens.%n",
                    source.getName(), action.getSynonyms().getFirst(), targetName);
            return false;
        }

        if (!receivableActions.contains(action)) {
            TextUtil.display("Nothing happens.%n");
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
            case Action.USE -> receiveUse(source);
        };
    }

    private boolean actionPredicate(Interactable source) {
        if (this.getPos() == null
                || source.getPos() == null
                || !(this.getPos().equals(source.getPos()))) {

            if (this instanceof Item item) {
                return item.getOwner() != null;
            }
            else return !(this instanceof Entity);
        }


        // can simplify this into a switch if needed in the future
        /*if (sourceEntity.hasSpecifiedStatus(new CrippledStatus()) &&
                (Math.random() < (double) sourceEntity.getStatusInstance(new CrippledStatus()).getAmplifier() / 10)) {
            return false;
        }

        if (sourceEntity.hasSpecifiedStatus(new FrozenStatus()) &&
                (Math.random() < (double) sourceEntity.getStatusInstance(new FrozenStatus()).getAmplifier() / 20)) {
            return false;
        }*/

        return true;
    }

    // You should kill yourself NOW!!
    public void annihilate() {
        this.remove();
        interactableMap.remove(this.ID);
    }

    public void remove() {
        this.setPos(null);
    }

    public Position getPos() {

        return position;
    }

    public void setPos(Position position) {
        if (position == null && this.position == null) {
            return;
        }

        if (position != null && position.equals(this.position)) {return;}

        // We add the interactable to the new node before we remove it from the previous one
        Node node = Node.findNode(position);
        if (node != null) {
            node.addInteractable(this);
        }

        // Removing the interactable from the previous node
        if (this.position != null
                && Node.findNode(this.position).getInteractables().contains(this)) {

            Node.findNode(this.position).removeInteractable(this);
        }

        // We finally set the position
        this.position = position;
    }

    public Node getNode() {
        return Node.findNode(position);
    }

    public Set<Action> getAvailableActions() {
        return availableActions;
    }

    public Set<Action> getReceivableActions() {
        return receivableActions;
    }

    public void setAvailableActions(Set<Action> availableActions) {
        this.availableActions = availableActions;
    }

    public void setReceivableActions(Set<Action> receivableActions) {
        this.receivableActions = receivableActions;
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
//        Interactable ended = this.ended == null
//                ? new InteractableEnded(name)
//                : this.ended;
//        Node node = Node.getNodeFromPosition(this.position);
//        node.removeInteractable(this);
//        node.addInteractable(ended);
        return true;
    }

    protected boolean receiveAbility(Interactable source) {
//        Interactable ended = this.ended == null
//                ? new InteractableEnded(name)
//                : this.ended;
//        Node node = Node.getNodeFromPosition(this.position);
//        node.removeInteractable(this);
//        node.addInteractable(ended);
        return true;
    }

    protected boolean receiveEquip(Interactable source) {

        return false;
    }

    protected boolean receiveMovement(Interactable source) {
        Position newPos = position.add(direction);

        if (Node.findNode(newPos) == null) {
            return false;
        }

        /*
        if (!Node.getNodeFromPosition(newPos).getEntrances().contains(source.direction.getOpposite())) {
            TextUtil.display(source,"%s tries to move but finds their path blocked. %n", source.getName());
            return false;
        }
        */

        TextUtil.display(source,"%s moves %s. %n", this.getName(), direction.toString().toLowerCase());
        this.setPos(newPos);
        return true;
    }


    protected boolean receiveTake(Interactable source) {

        return false;
    }

    protected boolean receiveSave(Interactable source) {

        return false;
    }

    protected boolean receiveResist(Interactable source) {

        return false;
    }

    protected boolean receiveDrop(Interactable source) {

        return false;
    }

    protected boolean receiveUse(Interactable source) {

        return false;
    }

    // Adds a status effect. Stacks the status according to the status' parameters defined in the Status interface
    public void addStatus(Status.Instance instance) {

        // Checks if the interactable is immune to the given status effect
        if (isImmuneTo(instance.getStatus())) {
            TextUtil.display(this,getDisplayName() + " is immune! %n");
            return;
        }

        if (instance.getAmplifier() == 0) {return;}

        if (instance.getDuration() == 0) {return;}

        Status status = instance.getStatus();
        instance.setTarget(this);

        /* Checks if the interactable already has the status applied,
           then stacks its duration/amplifier accordingly */
        if (this.hasSpecifiedStatus(status)) {
            Status.Instance tempInstance = this.getStatusInstance(status);

            if (status.stacksAmplifier()) {
                instance.setAmplifier(instance.getAmplifier() + tempInstance.getAmplifier());
            }

            if (status.stacksDuration()) {
                instance.setDuration(instance.getDuration() + tempInstance.getDuration());
            }
            // We remove the status before reapplying it if it did exist already
            removeStatus(status);
        }
        // This else displays the status application message,
        // since we know the interactable doesn't have it applied
        else {
            String statusName = status.getName();
            statusName = status.isHarmful() ? TextUtil.setColor(statusName, Color.red) : TextUtil.setColor(statusName, Color.green);
            TextUtil.display(this,"%s is now %s. %n", this.getName(),
                    statusName);
        }

        statusEffects.add(instance);
        // The instance's onApply effect is called here
        instance.onApply();
    }

    // Remove a status. Removing a status means removing an entire instance of that status, because Statuses can stack
    public void removeStatus(Status status) {
        statusEffects.removeIf(statusInstance -> statusInstance.getStatus().is(status));
    }

    // Triggered by the turn() function. Checks the entity's statuses and applies their effect accordingly.
    private void statusTurn() {
        if (hasStatus()) {
            List<Status.Instance> removeList = new ArrayList<>();
            for (Status.Instance statusInstance : statusEffects) {
                statusInstance.turn();
                if (statusInstance.getDuration() <= 0) {
                    removeList.add(statusInstance);
                }
            }
            for (Status.Instance statusInstance : removeList) {
                statusInstance.onEnded();
                removeStatus(statusInstance.getStatus());
                TextUtil.display(this,"%s is no longer %s. %n", getDisplayName(), statusInstance.getStatus().getName());
            }
        }
    }

    // Gets the instance of a status on the entity (if it has it)
    public Status.Instance getStatusInstance(Status status) {
        for (Status.Instance statusInstance : statusEffects) {
            if (status.is(statusInstance.getStatus())) {
                return statusInstance;
            }
        }
        return null;
    }

    // Check if the entity has a specific status applied to them
    public boolean hasSpecifiedStatus(Status status) {
        if (hasStatus()) {
            for (Status.Instance statusInstance : statusEffects) {
                if (statusInstance.getStatus().is(status)) {
                    return true;
                }

            }
        }
        return false;
    }

    // Checks if the entity has ANY status
    public boolean hasStatus() {
        if (statusEffects == null) {
            statusEffects = new ArrayList<>();
            return false;
        }
        return !(this.statusEffects.isEmpty());
    }

    // Gets the list of all statuses on the entity
    public List<Status.Instance> getStatuses() {

        return statusEffects;
    }

    public void setStatuses(List<Status.Instance> statusEffects) {
        this.statusEffects = statusEffects;
    }

    public void addImmunity(Status status) {
        for (Status status1 : immunities) {
            if (status1.is(status)) {
                return;
            }
        }
        immunities.add(status);
    }

    public void removeImmunity(Status immunity) {
        immunities.removeIf(immunity::is);
    }

    public List<Status> getImmunities() {
        return immunities;
    }

    public boolean isImmuneTo(Status status) {
        for (Status immunity : immunities) {
            if (immunity.is(status)) {
                return true;
            }
        }
        return false;
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
            if (ID.equals(modifier.getID())) {
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


    public void hurt(Interactable source, float amount, DamageType damageType) {

    }

    public void heal(Interactable source, float amount) {

    }

}
