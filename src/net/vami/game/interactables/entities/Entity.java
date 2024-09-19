package net.vami.game.interactables.entities;
import com.google.gson.annotations.JsonAdapter;
import net.vami.game.display.text.TextFormatter;
import net.vami.game.interactables.InteractableAdapter;
import net.vami.game.interactables.ai.Brain;
import net.vami.game.interactables.interactions.*;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.abilities.Ability;
import net.vami.game.interactables.interactions.abilities.RageAbility;
import net.vami.game.interactables.interactions.damagetypes.BluntDamage;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.*;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.equipables.ItemEquipable;
import net.vami.game.interactables.items.holdables.ItemHoldable;
import net.vami.game.world.Node;
import net.vami.game.world.Position;

import java.util.*;

public abstract class Entity extends Interactable {

    // Basic entity stats
    private float health;
    private int level;
    private boolean enemy;

    private Attributes attributes;

    // DamageType weaknesses and resistances as well as the statusEffect list and the status immunities
    private List<DamageType> weaknesses = new ArrayList<>();
    private List<DamageType> resistances = new ArrayList<>();
    private List<Status.Instance> statusEffects = new ArrayList<>();
    private List<Status> immunities = new ArrayList<>();

    // All item-related variables (like the inventory)
    private List<UUID> inventory = new ArrayList<>();
    private UUID heldItem;
    private int maxEquipSlots = 6;
    private List<UUID> equippedItems = new ArrayList<>();

    // This is mostly for the AI of the entity, not necessarily used for all entities
    private UUID target;

    public Entity(String name, Attributes attributes) {
        super(name);
        this.attributes = attributes;
        attributes.initialize();

        level = attributes.levelAttribute;
        health = attributes.maxHealthAttribute;

        // By default, all entities have these actions available and receivable
        // You can remove them manually if you wish using removeAvailableAction or removeReceivableAction
        addAvailableAction(Action.ATTACK);
        addAvailableAction(Action.ABILITY);
        addAvailableAction(Action.MOVEMENT);
        addAvailableAction(Action.RESIST);
        addAvailableAction(Action.TAKE);
        addAvailableAction(Action.EQUIP);
        addAvailableAction(Action.DROP);

        addReceivableAction(Action.ATTACK);
        addReceivableAction(Action.ABILITY);
        addReceivableAction(Action.RESIST);
        addReceivableAction(Action.MOVEMENT);

    }

    // Overrides the remove() in the Interactable to add entity drops baby
    @Override
    public void remove() {
        ArrayList<Item> dropList = new ArrayList<>();
        dropList.addAll(getInventory());
        dropList.addAll(getEquippedItems());
        if (this.hasHeldItem()) {
            dropList.add(getHeldItem());
        }

        for (Item item : dropList) {
            item.setPos(this.getPos());
            System.out.printf("%s dropped %s! %n", this.getDisplayName(), item.getName());
        }

        super.remove();
    }

    // A "tick" of the entity
    public void turn() {

        statusTurn();
    }

    public Brain getBrain() {
        return null;
    }

    // Main damage function
    @Override
    public void hurt(Entity source, float amount, DamageType damageType) {

        for (DamageType damageType1 : weaknesses) {
            if (damageType1.is(damageType)) {
                amount = amount * 2;
                break;
            }
        }

        for (DamageType damageType1 : resistances) {
            if (damageType1.is(damageType)) {
                amount = amount / 2;
                break;
            }
        }

        if (this.getArmor() > 0) {
            amount = amount - this.getArmor();
        }

        if (amount <= 0) {
            amount = 1;
        }

        health -= amount;

        TextFormatter.EntityInteraction.hurtEntity(new TextFormatter.EntityInteraction(this, source, amount, damageType));

        // Applies status instance based on the damage type dealt
        damageType.onHit(this, source, amount);
    }


    // Main healing function
    @Override
    public void heal(Entity source, float amount) {
        if (isEnded()) {
            return;
        }

        amount = EntityManager.Stats.heal(this, source, amount);

        TextFormatter.EntityInteraction.healEntity(new TextFormatter.EntityInteraction(
                this, source, amount));

        health = Math.min(this.getMaxHealth(), health + amount);
    }

    // Checks if the entity is alive
    @Override
    public boolean isEnded() {

        return health <= 0;
    }


    // Adds a status effect. Stacks the status according to the status' parameters defined in the Status interface
    @Override
    public void addStatus(Status.Instance status) {
        if (isImmuneTo(status.getStatus())) {
            System.out.println(getDisplayName() + " is immune!");
            return;
        }
        Status temp = status.getStatus();
        status.setTarget(this);
        if (this.hasSpecifiedStatus(temp)) {
            Status.Instance tempInstance = this.getStatusInstance(temp);

            if (temp.stacksAmplifier()) {
                status.setAmplifier(status.getAmplifier() + tempInstance.getAmplifier());
            }

            if (temp.stacksDuration()) {
               status.setDuration(status.getDuration() + tempInstance.getDuration());
            }

            removeStatus(temp);
        }
        else {
            System.out.printf("%s is now %s. %n", this.getName(), temp.getName());
        }

        statusEffects.add(status);
        status.onApply();
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
                System.out.printf("%s is no longer %s. %n", getDisplayName(), statusInstance.getStatus().getName());
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
        }
        return !(this.statusEffects.isEmpty());
    }

    // Gets the list of all statuses on the entity
    public List<Status.Instance> getStatuses() {

        return statusEffects;
    }



    // Adds a damage type resistance to the entity
    public void addResistance(DamageType resistance) {

        for (DamageType damageType : resistances) {
            if (resistance.is(damageType)) {
                return;
            }
        }
        resistances.add(resistance);
    }

    public void removeResistance(DamageType resistance) {
        resistances.removeIf(resistance::is);    }

    // Gets all the entity's damage type resistances
    public List<DamageType> getResistances() {

        return resistances;
    }

    // Adds a damage type weakness to the entity
    public void addWeakness(DamageType weakness) {
        for (DamageType damageType : weaknesses) {
            if (weakness.is(damageType)) {
                return;
            }
        }
        weaknesses.add(weakness);
    }

    public void removeWeakness(DamageType weakness) {
        weaknesses.removeIf(weakness::is);
    }

    // Gets all the entity's damage type weaknesses
    public List<DamageType> getWeaknesses() {

        return weaknesses;
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

    // Gets the entity health
    public float getHealth() {

        return this.health;
    }

    // Gets the entity max health
    public int getMaxHealth() {
        int amount;
        amount = attributes.maxHealthAttribute;

        amount += (int) Modifier.calculate(this.getModifiers(), ModifierType.MAX_HEALTH);

        return amount;
    }

    // Gets the entity's armor
    public int getArmor() {
        int amount = attributes.armorAttribute;

        amount += (int) Modifier.calculate(this.getModifiers(), ModifierType.ARMOR);

        return amount;
    }

    // Gets the entity's damage
    public float getDamage() {

        float amount = this.attributes.baseDamageAttribute;

        if (this.hasHeldItem()) {
            amount += this.getHeldItem().getDamage();
        }

        amount += Modifier.calculate(this.getModifiers(), ModifierType.DAMAGE);

        return amount;
    }

    public void setDamage(float amount) {
        attributes.baseDamageAttribute = amount;
    }

    // Gets the entity's damage type
    public DamageType getDamageType() {
        if (hasHeldItem()) {
            return getHeldItem().getDamageType();
        }
        return attributes.getDamageType();
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
    public Ability getAbility() {

        return attributes.abilityAttribute;
    }

    // Gets the current target of the entity
    public Entity getTarget() {

        return (Entity) Interactable.getInteractableFromID(this.target);
    }

    // Sets the current target of the entity
    public void setTarget(Entity target) {

        this.target = target.getID();
    }

    // Checks if the entity has a target
    public boolean hasTarget() {

        return getTarget() != null && !getTarget().isEnded();
    }

    // Gets the formatted display name of the entity
    public String getDisplayName() {

        return getName() + statusDisplay();
    }

    // Formatted level display of the entity
    public String levelDisplay() {

        return "[" + level + "]";
    }

    // Displays status brackets next to the entity name, used in getDisplayName()
    public String statusDisplay() {
        String display = "";
        String space = ", ";

        if (!statusEffects.isEmpty()) {
            for (Status.Instance statusInstance : statusEffects) {
                if (statusEffects.getLast() == statusInstance) {
                    space = "";
                }
                String statusName = statusInstance.getStatus().getName();
                statusName = statusInstance.getStatus().isHarmful() ? TextFormatter.red(statusName) : TextFormatter.green(statusName);
                display += statusName + space;
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

        float damage = entitySource.getDamage();
        DamageType type = entitySource.getDamageType();

        hurt(entitySource, damage, type);
        return true;
    }

    @Override
    public boolean receiveAbility(Interactable interactable) {
        if (!(interactable instanceof Entity sourceEntity)) {
            return false;
        }
        System.out.printf("%s casts %s on %s! %n",
                sourceEntity.getDisplayName(), ((Entity) interactable).getAbility().getName(), this.getDisplayName());

        return sourceEntity.getAbility().useAbility(sourceEntity, this);
    }

    @Override
    public boolean receiveResist(Interactable source) {
        Status.Instance instance1 = null;
        for (Status.Instance instance : statusEffects) {
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
        ArrayList<ItemEquipable> itemList = new ArrayList<>();
        for (UUID item : equippedItems) {
            itemList.add((ItemEquipable) Interactable.getInteractableFromID(item));
        }
        return itemList;
    }

    public void addEquippedItem(ItemEquipable item) {
        equippedItems.add(item.getID());
    }

    public boolean hasItemEquipped(ItemEquipable item) {
        for (ItemEquipable itemEquipable : getEquippedItems()) {
            if (itemEquipable == item) {
                return true;
            }
        }
        return false;
    }

    // Gets the max equipment slots for equipable items (ItemEquipable)
    public int getMaxEquipSlots() {

        return maxEquipSlots;
    }

    // Sets the max equipment slots for ItemEquipable items
    public void setMaxEquipSlots(int maxEquipSlots) {

        this.maxEquipSlots = maxEquipSlots;
    }

    public List<Item> getInventory() {
        ArrayList<Item> itemList = new ArrayList<>();
        for (UUID item : inventory) {
            itemList.add((Item) Interactable.getInteractableFromID(item));
        }
        return itemList;
    }

    // Adds an item to the inventory
    public void addInventoryItem(Item item) {
        inventory.add(item.getID());
        if (item.getPos() != null) {
            item.remove();
        }
    }

    // Removes an item from the inventory
    public void removeFromInventory(Item item) {

        inventory.remove(item.getID());
    }

    // Gets the item the entity is currently holding
    public ItemHoldable getHeldItem() {
        return (ItemHoldable) Interactable.getInteractableFromID(heldItem);
    }

    // Checks if the entity has an item in their hand
    public boolean hasHeldItem() {

        return !(getHeldItem() == null);
    }

    // Equips an item to the entity's hand.
    public void setHeldItem(ItemHoldable item) {

        heldItem = item.getID();
    }

    // Removes an item from holdable or equipable
    public boolean removeItem(Item item) {

        if (inventory.contains(item.getID())) {
            inventory.remove(item.getID());
            return true;
        }

        if (item instanceof ItemEquipable) {
            if (!equippedItems.contains(item.getID())) {
                return false;
            }
            equippedItems.remove(item.getID());
            return true;
        }
        else if (item instanceof ItemHoldable) {
            if (!hasHeldItem()) {
                return false;
            }
            heldItem = null;
            return true;
        }
        return false;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    // This class exists entirely because im a lazy piece of shit.
    // It lets me dynamically change the attributes of an entity both in its class and when instantiated.
    // I kinda took "inspiration" from Minecraft for this one
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
            this.damageTypeAttribute = null;
            this.abilityAttribute = null;
        }

        public void initialize() {
            if (levelAttribute == -1) {levelAttribute = 1;}
            if (maxHealthAttribute == -1) {maxHealthAttribute = 10 * levelAttribute;}
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
        public Attributes defaultDamageType(DamageType damageType) {
            if (damageTypeAttribute == null) {damageTypeAttribute = damageType;}
            return this;
        }
        public Attributes ability(Ability Ability) {
            if (abilityAttribute == null) {
                abilityAttribute = Ability;}
            return this;
        }

        public int getLevel() {return levelAttribute;}

        public int getMaxHealth() {return maxHealthAttribute;}

        public float getBaseDamage() {return baseDamageAttribute;}

        public int getArmor() {return armorAttribute;}

        public DamageType getDamageType() {return damageTypeAttribute;}

        public Ability getAbility() {return abilityAttribute;}

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
