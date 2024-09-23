package net.vami.game.interactables;
import com.google.gson.*;
import net.vami.game.interactables.entities.Player;
import net.vami.game.interactables.interactions.*;
import net.vami.game.world.Direction;
import net.vami.game.Game;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.interactions.statuses.CrippledStatus;
import net.vami.game.interactables.interactions.statuses.FrozenStatus;
import net.vami.util.ClassUtil;
import net.vami.util.HexUtil;
import net.vami.util.TextUtil;
import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.*;
import java.util.*;

public class Interactable {
    private final UUID ID;
    private String name;
    private Position position = null;
    private String description;
    private Set<Action> receivableActions = new HashSet<>();
    private Set<Action> availableActions = new HashSet<>();
//    private Interactable ended;
    private Direction direction;
    private static HashMap<UUID, Interactable> interactableMap = new HashMap<>();

    private List<Modifier> modifiers = new ArrayList<>();


    public Interactable(String name) {
        ID = UUID.randomUUID();
        interactableMap.put(ID, this);

        this.name = name;

        if (Node.getNodeFromPosition(position) != null) {
            Node.getNodeFromPosition(position).addInteractable(this);
        }

    }

    // Spawns an interactable with a defined position
    public static void spawn(Interactable interactable, Position position) {
        if (position == null) {
            position = new Position(0,0,0);
        }
        if (Node.getNodeFromPosition(position) == null) {
            return;
        }
        interactable.setPos(position);
    }

    // Spawns the entity at its default set position. If the position is null, it will spawn it at the anchor point (0, 0, 0)
    public static Interactable spawn(Interactable interactable) {
        Position position = interactable.position;
        if (position == null) {
            position = new Position(0,0,0);
        }

        if (Node.getNodeFromPosition(position) == null) {
            return null;
        }
        interactable.setPos(position);
        return interactable;
    }

    public static void saveInteractables(Player player) {
        String saveFilePath = Game.interactableSavePathFormat.replace("%", HexUtil.toHex(player.getName()));
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonArray interactableArray = new JsonArray();
        for (Interactable interactable : interactableMap.values()) {
            if (interactable.getClass().equals(Player.class)) {
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
                    interactableMap.put(interactable.ID, interactable);
                    interactable.setPos(interactable.position);
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

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public boolean receiveAction(Interactable source, Action action) {
        if (!actionPredicate(source)) {
            return false;
        }

        if (!source.availableActions.contains(action)) {
            TextUtil.display(this,"%s tries to %s %s, but nothing happens. %n",
                    source.getName(), action.getSynonyms().getFirst(), this.getName());
            return false;
        }

        if (!receivableActions.contains(action)) {
            AnsiConsole.out.println("Nothing happens.");
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
        if (position == null && this.position == null) {return;}

//        if (position != null && position.equals(this.position)) {return;}

        if (this.position != null
                && Node.getNodeFromPosition(this.position).getInteractables().contains(this)) {

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
//        Interactable ended = this.ended == null
//                ? new InteractableEnded(name)
//                : this.ended;
//        Node node = Node.getNodeFromPosition(this.position);
//        node.removeInteractable(this);
//        node.addInteractable(ended);
        return true;
    }

    public boolean receiveAbility(Interactable source) {
//        Interactable ended = this.ended == null
//                ? new InteractableEnded(name)
//                : this.ended;
//        Node node = Node.getNodeFromPosition(this.position);
//        node.removeInteractable(this);
//        node.addInteractable(ended);
        return true;
    }

    public boolean receiveEquip(Interactable source) {

        return false;
    }

    public boolean receiveMovement(Interactable source) {
        Position newPos = position.add(direction);

        if (Node.getNodeFromPosition(newPos) == null) {
            return false;
        }

        this.setPos(newPos);
        TextUtil.display(this,"%s moves %s. %n", this.getName(), direction.toString().toLowerCase());
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

    public boolean receiveUse(Interactable source) {

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
