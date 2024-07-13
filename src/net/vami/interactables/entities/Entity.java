package net.vami.interactables.entities;
import net.vami.game.display.TextFormatter;
import net.vami.interactables.ai.Brain;
import net.vami.interactables.interactions.*;
import net.vami.interactables.Interactable;
import net.vami.interactables.interactions.abilities.IAbility;
import net.vami.interactables.interactions.abilities.RageAbility;
import net.vami.interactables.interactions.damagetypes.BluntDamage;
import net.vami.interactables.interactions.damagetypes.IDamageType;
import net.vami.interactables.interactions.statuses.*;
import net.vami.interactables.items.Item;
import net.vami.interactables.items.ItemEquipable;
import net.vami.interactables.items.ItemHoldable;

import java.util.*;

public abstract class Entity extends Interactable {

    // Basic entity stats
    private int maxHealth;
    private float health;
    private float baseDamage;
    private int armor;
    private IDamageType defaultDamageType;
    private int level;
    private IAbility iAbility;
    private boolean enemy;

    private Attributes attributes;

    // DamageType weaknesses and resistances as well as the statusEffect list
    private List<IDamageType> weaknesses = new ArrayList<>();
    private List<IDamageType> resistances = new ArrayList<>();
    private List<IStatus.Instance> statusEffects = new ArrayList<>();

    // All item-related variables (like the inventory)
    private List<Item> inventory = new ArrayList<>();
    private ItemHoldable heldItem;
    private int maxEquipSlots = 3;
    private List<ItemEquipable> equippedItems = new ArrayList<>(maxEquipSlots);

    // This is mostly for the AI of the entity, not necessarily used for all entities
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
        iAbility = attributes.abilityAttribute;
        health = maxHealth;

        // By default, all entities have these actions available and receivable
        // You can remove them manually if you wish using removeAvailableAction or removeReceivableAction
        addAvailableAction(Action.ATTACK);
        addAvailableAction(Action.ABILITY);
        addAvailableAction(Action.MOVEMENT);
        addReceivableAction(Action.RESIST);

        addReceivableAction(Action.ATTACK);
        addReceivableAction(Action.ABILITY);
        addAvailableAction(Action.MOVEMENT);
        addReceivableAction(Action.RESIST);

    }

    // A "tick" of the entity
    public void turn() {

        statusTurn();
    }

    public abstract Brain getBrain();

    // Main damage function
    @Override
    public void hurt(Entity source, float amount, IDamageType damageType) {

        amount = EntityManager.Calc.damage(this, source, amount, damageType);
        health -= amount;

        TextFormatter.EntityInteraction.hurtEntity(new TextFormatter.EntityInteraction(
                this, source, amount, damageType));

        // Applies status instance based on the damage type dealt
        damageType.onHit(this, source, amount);
    }


    // Main healing function
    @Override
    public void heal(Entity source, float amount) {
        if (isEnded()) {
            return;
        }

        amount = EntityManager.Calc.heal(this, source, amount);

        TextFormatter.EntityInteraction.healEntity(new TextFormatter.EntityInteraction(
                this, source, amount));

        health = Math.min(maxHealth, health + amount);
    }

    // Uses the ability of the entity
    @Override
    public boolean useAbility(Interactable target) {
        iAbility.useAbility(this, target);
        return true;
    }

    // Checks if the entity is alive
    @Override
    public boolean isEnded() {

        return health <= 0;
    }


    // Adds a status effect. Stacks the status according to the status' parameters defined in the Status class
    @Override
    public void addStatus(IStatus.Instance status) {
        IStatus temp = status.getStatus();

        if (this.hasSpecifiedStatus(temp)) {
            IStatus.Instance tempInstance = this.getStatusInstance(temp);

            if (temp.stacksAmplifier()) {
                status.setAmplifier(status.getAmplifier() + tempInstance.getAmplifier());
            }

            if (temp.stacksDuration()) {
               status.setDuration(status.getDuration() + tempInstance.getDuration());
            }

            removeStatus(temp);
        }
        else {
            status.onApply();
            System.out.printf("%s is now %s. %n", this.getName(), temp.getName());
        }
        status.setTarget(this);
        statusEffects.add(status);
    }

    // Remove a status. Removing a status means removing an entire instance of that status, because Statuses can stack
    public void removeStatus(IStatus status) {
            statusEffects.removeIf(statusInstance -> statusInstance.getStatus().is(status));
    }

    // Triggered by the turn() function. Checks the entity's statuses and applies their effect accordingly.
    private void statusTurn() {
        if (hasStatus()) {
            List<IStatus.Instance> removeList = new ArrayList<>();
            for (IStatus.Instance statusInstance : statusEffects) {
                statusInstance.turn();
                if (statusInstance.getDuration() <= 0) {
                    removeList.add(statusInstance);
                }
            }
            for (IStatus.Instance statusInstance : removeList) {
                statusInstance.onEnded();
                removeStatus(statusInstance.getStatus());
                System.out.printf("%s is no longer %s. %n", getDisplayName(), statusInstance.getStatus().getName());
            }
        }
    }

    // Gets the instance of a status on the entity (if it has it)
    public IStatus.Instance getStatusInstance(IStatus status) {

        for (IStatus.Instance statusInstance : statusEffects) {
            if (status.is(statusInstance.getStatus())) {
                return statusInstance;
            }
        }
        return null;
    }

    // Check if the entity has a specific status applied to them
    public boolean hasSpecifiedStatus(IStatus status) {
        if (hasStatus()) {
            for (IStatus.Instance statusInstance : statusEffects) {
                if (statusInstance.getStatus().is(status)) {
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

    // Gets the list of all statuses on the entity
    public List<IStatus.Instance> getStatuses() {

        return statusEffects;
    }



// Adds a damage type resistance to the entity
    public void addResistance(IDamageType resistance) {

        resistances.add(resistance);
    }

    // Gets all the entity's damage type resistances
    public List<IDamageType> getResistances() {

        return resistances;
    }

    // Adds a damage type weakness to the entity
    public void addWeakness(IDamageType weakness) {

        weaknesses.add(weakness);
    }

    // Gets all the entity's damage type weaknesses
    public List<IDamageType> getWeaknesses() {

        return weaknesses;
    }

    // Gets the entity health
    public float getHealth() {

        return this.health;
    }

    // Gets the entity max health
    public int getMaxHealth() {

        return this.maxHealth;
    }

    public int getArmor() {

        return this.armor;
    }

    // Gets the entity's base damage
    public float getBaseDamage() {

        return baseDamage;
    }

    // Gets the entity's damage type
    public IDamageType getDefaultDamageType() {

        return defaultDamageType;
    }


    // Checks of the entity is an enemy (against the player)
    public boolean isEnemy() {

        return enemy;
    }

    // Sets the entity to an enemy
    public void setEnemy(boolean enemy) {

        this.enemy = enemy;
    }

    // Gets the level of the entity
    public int getLevel() {

        return level;
    }

    // Gets the entity's ability
    public IAbility getAbility() {

        return iAbility;
    }

    // Gets the current target of the entity
    public Entity getTarget() {

        return this.target;
    }

    // Sets the current target of the entity
    public void setTarget(Entity target) {

        this.target = target;
    }

    // Checks if the entity has a target
    public boolean hasTarget() {

        return target != null && !target.isEnded();
    }

    // Gets the formatted display name of the entity
    public String getDisplayName() {

        return getName() + statusDisplay();
    }

    // Formatted level display of the entity
    public String levelDisplay() {

        return "[" + level + "]";
    }

    public String statusDisplay() {
        String display = "";
        String space = ", ";

        if (!statusEffects.isEmpty()) {
            for (IStatus.Instance statusInstance : statusEffects) {
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
        IDamageType type = entitySource.getDefaultDamageType();

        if (entitySource.hasHeldItem()) {
            damage += entitySource.getHeldItem().getBaseDamage();
            if (entitySource.getHeldItem().getDamageType() != null) {type = entitySource.getHeldItem().getDamageType();}
        }

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

    @Override
    public boolean receiveResist(Interactable source) {
        IStatus.Instance instance1 = null;
        for (IStatus.Instance instance : statusEffects) {
            if (new Random().nextInt(instance.getAmplifier() + 1) == 1) {
                instance1 = instance;
                break;
            }
        }

        if (instance1 == null) {
            return false;
        }

        removeStatus(instance1.getStatus());
        System.out.printf("%s resisted and lost %s. %n", source.getName(), instance1.getStatus().getName());
        return true;
    }

    // Gets all the equipped items of the entity
    public List<ItemEquipable> getEquippedItems() {

        return equippedItems;
    }

    // Gets the max equipment slots for equipable items (ItemEquipable)
    public int getMaxEquipSlots() {

        return maxEquipSlots;
    }

    // Sets the max equipment slots for ItemEquipable items
    public void setMaxEquipSlots(int maxEquipSlots) {

        this.maxEquipSlots = maxEquipSlots;
    }

    // Adds an item to the inventory
    public void addInventoryItem(Item item) {
        inventory.add(item);
        item.kill();
    }

    // Removes an item from the inventory
    public void removeInventoryItem(Item item) {

        inventory.remove(item);
    }

    // Gets the item the entity is currently holding
    public ItemHoldable getHeldItem() {

        return heldItem;
    }

    // Checks if the entity has an item in their hand
    public boolean hasHeldItem() {

        return !(heldItem == null);
    }

    // Equips an item to the entity's hand.
    // If the item is ItemEquipable it takes from the maxEquipSlots, else it holds the item instead
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

    // Removes an item from holdable or equipable
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

    // This class exists entirely because im a lazy piece of shit.
    // It lets me dynamically change the attributes of an entity both in its class and when instantiated.
    // I kinda took "inspiration" from Minecraft for this one
    public static class Attributes {
        int levelAttribute;
        int maxHealthAttribute;
        float baseDamageAttribute;
        int armorAttribute;
        IDamageType damageTypeAttribute;
        IAbility abilityAttribute;

        public Attributes() {
            this.levelAttribute = -1;
            this.maxHealthAttribute = -1;
            this.baseDamageAttribute = -1;
            this.armorAttribute = -1;
            this.damageTypeAttribute = null;
            this.abilityAttribute = null;
        }

        public void setDefaults() {
            if (levelAttribute == -1) {levelAttribute = 1;}
            if (maxHealthAttribute == -1) {maxHealthAttribute = 20 * levelAttribute;}
            if (baseDamageAttribute == -1) {baseDamageAttribute = levelAttribute;}
            if (armorAttribute == -1) {armorAttribute = levelAttribute;}
            if (damageTypeAttribute == null) {damageTypeAttribute = new BluntDamage();}
            if (abilityAttribute == null) {abilityAttribute = new RageAbility();}
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
        public Attributes defaultDamageType(IDamageType damageType) {
            if (damageTypeAttribute == null) {damageTypeAttribute = damageType;}
            return this;
        }
        public Attributes ability(IAbility IAbility) {
            if (abilityAttribute == null) {
                abilityAttribute = IAbility;}
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
