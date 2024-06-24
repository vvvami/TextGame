package net.vami.interactables.entities;
import net.vami.interactables.interactions.*;
import net.vami.game.Main;
import net.vami.interactables.Interactable;
import net.vami.interactables.interactions.abilities.Ability;
import net.vami.interactables.interactions.abilities.FlamesAbility;
import net.vami.interactables.interactions.abilities.NoneAbility;
import net.vami.interactables.interactions.Status;
import net.vami.interactables.interactions.statuses.BurningStatus;
import net.vami.interactables.interactions.statuses.CrippledStatus;
import net.vami.interactables.interactions.statuses.FrozenStatus;
import net.vami.interactables.interactions.statuses.WoundedStatus;
import net.vami.interactables.items.Item;
import net.vami.interactables.items.ItemEquipable;
import net.vami.interactables.items.ItemHoldable;
import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;
import java.util.*;

public abstract class Entity extends Interactable {


    private int maxHealth;
    private float health;
    private float baseDamage;
    private int armor;
    private DamageType defaultDamageType;
    private int level;

    private Attributes attributes;

    private List<DamageType> weaknesses = new ArrayList<>();
    private List<DamageType> resistances = new ArrayList<>();
    private List<Status.Instance> statusEffects = new ArrayList<>();

    private List<Item> inventory = new ArrayList<>();
    private ItemHoldable heldItem;
    private List<ItemEquipable> equippedItems = new ArrayList<>();
    private int maxEquipSlots = 3;

    private boolean enemy;
    private Ability ability;
    private Entity target;

    public Entity(String name, Attributes attributes) {
        super(name);
        this.attributes = attributes;
        attributes.setDefaults();

        level = attributes.levelAttribute;
        maxHealth = attributes.maxHealthAttribute;
        baseDamage = attributes.baseDamageAttribute;
        armor = attributes.armorAttribute;
        defaultDamageType = attributes.damageTypeAttribute;
        ability = attributes.abilityAttribute;
        health = maxHealth;



        addAvailableAction(Action.ATTACK);
        addAvailableAction(Action.ABILITY);
        addAvailableAction(Action.MOVEMENT);

        addReceivableAction(Action.ATTACK);
        addReceivableAction(Action.ABILITY);
        addAvailableAction(Action.MOVEMENT);

    }

    public void turn() {

        statusTurn();
    }

    // Main damage function
    @Override
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
            case SHARP -> attackStatus = WoundedStatus.STATUS;
            case BLUNT -> attackStatus = CrippledStatus.STATUS;
            case ICE -> attackStatus = FrozenStatus.STATUS;
            case FIRE -> attackStatus = BurningStatus.STATUS;
        }

        if (attackStatus != null) {
            Status.Instance attackStatusInstance = new Status.Instance(attackStatus, (int) amount, (int) amount * 2, source);
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
        ability.useAbility(this, target);
        return true;
    }

    // Checks if the entity is alive
    @Override
    public boolean isEnded() {

        return health <= 0;
    }


    // Adds a status effect. Stacks the status according to the status' parameters defined in the Status class
    @Override
    public void addStatus(@NotNull Status.Instance status) {
        if (this.hasSpecifiedStatus(status.getStatus())) {
            if (status.getStatus().stacksAmplifier()) {
                status.setAmplifier(status.getAmplifier() + this.getStatusInstance(status.getStatus()).getAmplifier());
            }
            if (status.getStatus().stacksDuration()) {
               status.setDuration(status.getDuration() + this.getStatusInstance(status.getStatus()).getDuration());
            }
            removeStatus(status.getStatus());
        }
        else {
            status.onApply();
            System.out.printf("%s is now %s. %n", this.getName(), status.getStatus().getName());
        }
        status.setTarget(this);
        statusEffects.add(status);
    }

    // Remove a status. Removing a status means removing an entire instance of that status, because Statuses can stack
    public void removeStatus(Status status) {
            statusEffects.removeIf(statusInstance -> statusInstance.getStatus() == status);
    }

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
                System.out.printf("%s is no longer %s. %n", getDisplayName(), statusInstance.getStatus().getName());
            }
        }
    }

// Gets the instance of a status on the entity (if it has it)
    public Status.Instance getStatusInstance(Status status) {

        for (Status.Instance statusInstance : statusEffects) {
            if (status.equals(statusInstance.getStatus())) {
                return statusInstance;
            }
        }
        return null;
    }

// Check if the entity has a specific status applied to them
    public boolean hasSpecifiedStatus(Status status) {
        if (hasStatus()) {
            for (Status.Instance statusInstance : statusEffects) {
                if (statusInstance.getStatus().equals(status)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if the entity has ANY status
    public boolean hasStatus() {

        return !(this.statusEffects.isEmpty());
    }


    public List<Status.Instance> getEntityStatuses() {

        return statusEffects;
    }



// Adds a damagetype resistance to the entity
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
            for (Status.Instance statusInstance : statusEffects) {
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
    public boolean receiveAttack(Interactable source) {
        if (!(source instanceof Entity entitySource)) {
            return false;
        }
        float damage = entitySource.getBaseDamage();
        DamageType type = entitySource.getDefaultDamageType();

        if (entitySource.hasHeldItem()) {
            damage += entitySource.getHeldItem().getBaseDamage();
            if (entitySource.getHeldItem().getDamageType() != DamageType.NONE) {type = entitySource.getHeldItem().getDamageType();}
        }

        System.out.printf("%s was hit by %s! %n", this.getDisplayName(), entitySource.getDisplayName());
        hurt(entitySource, damage, type);
        return true;
    }

    @Override
    public boolean receiveAbility(Interactable interactable) {
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
                addInventoryItem(item);
                return true;
            }

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

    public static class Attributes {
        int levelAttribute;
        int maxHealthAttribute;
        float baseDamageAttribute;
        int armorAttribute;
        DamageType damageTypeAttribute;
        Ability abilityAttribute;

        public Attributes() {
            this.levelAttribute = -1;
            this.maxHealthAttribute = -1;
            this.baseDamageAttribute = -1;
            this.armorAttribute = -1;
            this.damageTypeAttribute = DamageType.NONE;
            this.abilityAttribute = NoneAbility.ABILITY;
        }

        public void setDefaults() {
            if (levelAttribute == -1) {levelAttribute = 1;}
            if (maxHealthAttribute == -1) {maxHealthAttribute = 20 * levelAttribute;}
            if (baseDamageAttribute == -1) {baseDamageAttribute = levelAttribute;}
            if (armorAttribute == -1) {armorAttribute = levelAttribute;}
            if (damageTypeAttribute == DamageType.NONE) {damageTypeAttribute = DamageType.BLUNT;}
            if (abilityAttribute == NoneAbility.ABILITY) {abilityAttribute = FlamesAbility.ABILITY;}
        }

        public Attributes level(int level) {
            if (levelAttribute == -1) {levelAttribute = level;}
            return this;
        }
        public Attributes maxHealth(int maxHealth) {
            if (maxHealthAttribute == -1) {maxHealthAttribute = maxHealth;}
            return this;
        }
        public Attributes baseDamage(float baseDamage) {
            if (baseDamageAttribute == -1) {baseDamageAttribute = baseDamage;}
            return this;
        }
        public Attributes armor(int armor) {
            if (armorAttribute == -1) {armorAttribute = armor;}
            return this;
        }
        public Attributes defaultDamageType(DamageType damageType) {
            if (damageTypeAttribute == DamageType.NONE) {damageTypeAttribute = damageType;}
            return this;
        }
        public Attributes ability(Ability ability) {
            if (abilityAttribute == NoneAbility.ABILITY) {abilityAttribute = ability;}
            return this;
        }

        public Attributes copyOf(Entity entity) {
            Attributes attributes = entity.attributes;
            this.levelAttribute = attributes.levelAttribute;
            this.maxHealthAttribute = attributes.maxHealthAttribute;
            this.baseDamageAttribute = attributes.baseDamageAttribute;
            this.armorAttribute = attributes.armorAttribute;
            this.damageTypeAttribute = attributes.damageTypeAttribute;
            this.abilityAttribute = attributes.abilityAttribute;
            return this;
        }
    }
}
