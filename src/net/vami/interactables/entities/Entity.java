package net.vami.interactables.entities;
import net.vami.game.interactions.*;
import net.vami.game.Main;
import net.vami.game.world.Position;
import net.vami.interactables.Interactable;
import net.vami.interactables.items.Item;
import net.vami.interactables.items.ItemEquipable;
import net.vami.interactables.items.ItemHoldable;
import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Entity extends Interactable {


    private int maxHealth;
    private float health;
    private float baseDamage;
    private int armor;
    private DamageType defaultDamageType;
    private int level;

    private List<DamageType> weaknesses = new ArrayList<>();
    private List<DamageType> resistances = new ArrayList<>();
    private List<StatusInstance> statusEffects = new ArrayList<>();

    private List<Item> inventory = new ArrayList<>();
    private ItemHoldable heldItem;
    private List<ItemEquipable> equippedItems = new ArrayList<>();
    private int maxEquipSlots = 3;

    private boolean enemy;
    private Ability ability;
    private Entity target;


    public Entity(String name, Position position, int level, int maxHealth, float baseDamage,
                  int armor, DamageType defaultDamageType, boolean enemy, Ability ability) {
        super(name, null, position);
        this.level = level;
        this.maxHealth = maxHealth;
        this.baseDamage = baseDamage;
        this.armor = armor;
        health = this.maxHealth;
        this.defaultDamageType = defaultDamageType;
        this.ability = ability;
        this.enemy = enemy;
        addAvailableAction(Action.ATTACK);
        addAvailableAction(Action.ABILITY);
        addAvailableAction(Action.MOVEMENT);
        addReceivableAction(Action.ATTACK);
        addReceivableAction(Action.ABILITY);
        addAvailableAction(Action.MOVEMENT);

    }

    // Main damage function
    public void hurt(Entity source, float amount, DamageType damageType) {

        if (armor > 0) {
            amount = (float) (amount / Math.sqrt(armor));
        }

        if (weaknesses.contains(damageType)) {
            amount = amount * 2;
        }

        if (resistances.contains(damageType)) {
            amount = amount / 2;
        }

        health -= amount;
        System.out.printf("%s suffered %s damage! %n", getDisplayName(), Main.ANSI_YELLOW
                + new DecimalFormat("##.##").format(amount)
                + Main.ANSI_RESET + " " + damageType.getName());

        Status attackStatus = null;
        switch (damageType) {
            case SHARP -> attackStatus = Status.BLEEDING;
            case BLUNT -> attackStatus = Status.CRIPPLED;
            case ICE -> attackStatus = Status.FROZEN;
            case FIRE -> attackStatus = Status.BURNING;
        }

        if (attackStatus != null) {
            StatusInstance attackStatusInstance = new StatusInstance(attackStatus, (int) amount, (int) amount * 2, source);
            if (Math.random() > 0.9) {
                addStatus(attackStatusInstance);
            }
        }
    }

    // Main healing function
    @Override
    public void heal(Entity source, float amount) {
        String stringAmount = Main.ANSI_YELLOW + amount + Main.ANSI_RESET;
        if (isEnded()) {
            return;
        }
        health = Math.min(maxHealth, health + amount);
        System.out.printf("%s was healed by %s for %s health! %n", getDisplayName(), source.getDisplayName(),
                 stringAmount);
    }

    @Override
    public boolean useAbility(Interactable target) {
        Ability.useAbility(target, this, ability);
        return true;
    }

    // Checks if the entity is alive
    @Override
    public boolean isEnded() {

        return health <= 0;
    }

    // Adds a status effect. Stacks the status according to the status' parameters defined in the Status enum
    @Override
    public void addStatus(@NotNull StatusInstance status) {
        if (this.hasSpecifiedStatus(status.getStatus())) {
            if (status.getStatus().stacksAmplifier()) {
                status.setAmplifier(status.getAmplifier() + this.getEntityStatus(status.getStatus()).getAmplifier());
            }
            if (status.getStatus().stacksDuration()) {
               status.setDuration(status.getDuration() + this.getEntityStatus(status.getStatus()).getDuration());
            }
            removeStatus(status.getStatus());
        }
        else {
            System.out.printf("%s is now %s. %n", this.getName(), status.getStatus().getName());
        }
        status.setTarget(this);
        statusEffects.add(status);
    }

    // Remove a status. Removing a status means removing an entire instance of that status, because Statuses can stack
    public void removeStatus(Status status) {
            statusEffects.removeIf(statusInstance -> statusInstance.getStatus() == status);
    }

    public void turn() {
        statusTurn();
    }

    private void statusTurn() {
        if (hasStatus()) {
            List<StatusInstance> removeList = new ArrayList<>();
            for (StatusInstance statusInstance : statusEffects) {
                statusInstance.turn();
                if (statusInstance.getDuration() <= 0) {
                    removeList.add(statusInstance);
                }
            }
            for (StatusInstance statusInstance : removeList) {
                removeStatus(statusInstance.getStatus());
                System.out.printf("%s is no longer %s. %n", getDisplayName(), statusInstance.getStatus().getName());
            }
        }
    }

    public void addResistance(DamageType resistance) {
        resistances.add(resistance);
    }

    public List<DamageType> getResistances() {
        return resistances;
    }

    public void addWeakness(DamageType weakness) {
        weaknesses.add(weakness);
    }

    public List<DamageType> getWeaknesses() {
        return weaknesses;
    }

    public StatusInstance getEntityStatus(Status status) {

            for (StatusInstance statusInstance : statusEffects) {
                if (status.equals(statusInstance.getStatus())) {
                    return statusInstance;
                }
        }
        return null;
    }

    public boolean hasSpecifiedStatus(Status status) {
        if (hasStatus()) {
            for (StatusInstance statusInstance : statusEffects) {
                if (statusInstance.getStatus().equals(status)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasStatus() {

        return !(this.statusEffects.isEmpty());
    }

    public List<StatusInstance> getEntityStatuses() {
        return statusEffects;
    }

    public float getHealth() {

        return this.health;
    }

    public float getBaseDamage() {

        return baseDamage;
    }

    public DamageType getDefaultDamageType() {
        return defaultDamageType;
    }

    // Checks of the entity is an enemy (against the player)
    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    public int getLevel() {
        return level;
    }

    public Ability getAbility() {
        return ability;
    }

    public Entity getTarget() {
        return this.target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public String getDisplayName() {
        return getName() + statusDisplay();
    }

    public String levelDisplay() {
        return "[" + level + "]";
    }

    public String statusDisplay() {
        String display = "";
        String space = ", ";

        if (!statusEffects.isEmpty()) {
            for (StatusInstance statusInstance : statusEffects) {
                if (statusEffects.getLast() == statusInstance) {
                    space = "";
                }
                display += statusInstance.getStatus().getName() + space;
            }
            display = " (" + display + ")";
        }
        return display;
    }


    @Override
    protected boolean receiveAttack(Interactable source) {
        if (!(source instanceof Entity)) {
            return false;
        }
        Entity entitySource = (Entity) source;
        float damage = entitySource.getBaseDamage();
        DamageType type = entitySource.getDefaultDamageType();

        if (entitySource.hasHeldItem()) {
            damage += entitySource.getHeldItem().getBaseDamage();
            type = entitySource.getHeldItem().getDamageType();
        }

        System.out.printf("%s was hit by %s! %n", getDisplayName(), entitySource.getDisplayName());
        hurt(entitySource, damage, type);
        return true;
    }

    @Override
    protected boolean receiveAbility(Interactable interactable) {
        if (!(interactable instanceof Entity)) {
            return false;
        }

        interactable.useAbility(this);
        return true;
    }

    public List<ItemEquipable> getEquippedItems() {
        return equippedItems;
    }

    public int getMaxEquipSlots() {
        return maxEquipSlots;
    }

    public void setMaxEquipSlots(int maxEquipSlots) {
        this.maxEquipSlots = maxEquipSlots;
    }

    public void addInventoryItem(Item item) {
        inventory.add(item);
        item.kill();
    }

    public void removeInventoryItem(Item item) {
        inventory.remove(item);
    }

    public ItemHoldable getHeldItem() {

        return heldItem;
    }

    public boolean hasHeldItem() {

        return !(heldItem == null);
    }

    public boolean equipItem(Item item) {

        if (item instanceof ItemEquipable) {
            if (equippedItems.size() >= maxEquipSlots) {
                return false;
            }

            item.setOwner(this);
            equippedItems.add((ItemEquipable) item);
            return true;
        }

        else if (item instanceof ItemHoldable) {
            if (hasHeldItem()) {
                addInventoryItem(heldItem);
            }

            item.setOwner(this);
            heldItem = (ItemHoldable) item;
            return true;
        }

        return false;
    }

    public boolean removeItem(Item item) {
        if (item instanceof ItemEquipable) {
            if (!equippedItems.contains(item)) {
                return false;
            }
            equippedItems.remove(item);
        }
        else if (item instanceof ItemHoldable) {
            if (!hasHeldItem()) {
                return false;
            }
            heldItem = null;
        }
        addInventoryItem(item);
        return true;
    }
}
