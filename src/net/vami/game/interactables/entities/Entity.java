package net.vami.game.interactables.entities;
import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.interactions.action.ActionFeedback;
import net.vami.game.interactables.interactions.modifier.Modifier;
import net.vami.game.interactables.interactions.modifier.ModifierType;
import net.vami.game.interactables.items.attunement.AttunableItem;
import net.vami.game.world.Position;
import net.vami.util.CalcUtil;
import net.vami.util.LogUtil;
import net.vami.util.TextUtil;
import net.vami.game.interactables.ai.Brain;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.abilities.Ability;
import net.vami.game.interactables.interactions.abilities.RageAbility;
import net.vami.game.interactables.interactions.damagetypes.BluntDamage;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.*;
import net.vami.game.interactables.items.BreakableItem;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.ItemEquipable;
import net.vami.game.interactables.items.ItemHoldable;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/** This is the basic Entity class. It contains all the things you need for a basic entity,
 * such as health, attributes, basic AI capabilities, an inventory and a bunch of methods. */
public abstract class Entity extends Interactable {

    // Basic logic
    private boolean enemy;

    // Basic entity stats
    private Attributes attributes;
    private float health;

    // DamageType weaknesses and resistances as well as the statusEffect list and the status immunities
    private List<DamageType> weaknesses = new ArrayList<>();
    private List<DamageType> resistances = new ArrayList<>();


    // All item-related variables (like the inventory)
    private List<UUID> inventory = new ArrayList<>();
    private UUID heldItem;
    private int maxEquipSlots = 6;
    private List<UUID> equippedItems = new ArrayList<>();

    // This is mostly for the AI of the entity, not necessarily used for all entities
    private UUID target;

    public Entity(String name, Attributes attributes) {
        super(name);
        attributes.initialize();
        health = attributes.maxHealthAttribute;
        this.attributes = attributes;

        /* By default, all entities have these actions available and receivable
        You can remove them manually if you wish using removeAvailableAction or removeReceivableAction */
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
        setStatuses(new ArrayList<>());
        ArrayList<Item> dropList = new ArrayList<>();
        dropList.addAll(getInventory());
        dropList.addAll(getEquippedItems());
        if (this.hasHeldItem()) {
            dropList.add(getHeldItem());
        }

        for (Item item : dropList) {
            item.setPos(this.getPos());
            if (item == dropList.getLast()) {
                Game.playSound(this, Sound.ITEM_DROP, 65);
            }
            TextUtil.display(this,"%s dropped %s! %n", this.getName(), item.getDisplayName());
        }
        super.remove();
    }

    @Override
    public void turn() {
        super.turn();
        itemTurn();
        LogUtil.Log("Entity ticked: (%s [%s], %s, %s)", this.getName(), this.getPos().toString(), this.getID(), this);
    }

    public Brain getBrain() {
        return null;
    }

    // Main damage function
    @Override
    public void hurt(Interactable source, float amount, DamageType damageType) {

        // The damage will always be at least 1
        amount = Math.max(1, amount);
        float finalAmount = CalcUtil.damage(source, this, amount, damageType);

        // Denies damage if the target is already dead
        if (finalAmount == 0) {return;}

        // Play damagetype sound
        if (damageType.getSound() == null) {
            Game.playSound(this, Sound.BLUNT_DAMAGE, 65);
        } else {
            Game.playSound(this, damageType.getSound(), 65);
        }

        // Reduce the target's health
        health -= finalAmount;

        // Game displays the hurt message
        String sourceName = "";
        if (source != null) {
            sourceName = source.getDisplayName();
        }
        ActionFeedback.HURT.printFeedback(this.getDisplayName(), sourceName,
                TextUtil.setColor(new DecimalFormat("##.##").format(finalAmount), Color.orange),
                damageType.getName());

        // Applies a status instance based on the damage type dealt
        damageType.onHit(this, source, finalAmount);

        // Checks if the source is an entity and has a held item
        // If it's breakable, it will reduce its durability
        // If it's attunable, it will trigger the attunement onHit()
        if (source instanceof Entity sourceEntity
                && sourceEntity.hasHeldItem()) {

            ItemHoldable heldItem = sourceEntity.getHeldItem();

            if (heldItem instanceof BreakableItem breakableItem &&
            breakableItem.damageOnHit()) {
                heldItem.hurt(1);
            }

            // Item on hit effect
            heldItem.onHit(source, this, damageType, amount);

            if (heldItem instanceof AttunableItem
            && heldItem.hasAttunement()) {
                heldItem.getAttunement()
                        .onHit(sourceEntity.getHeldItem(), source, this, amount, damageType);
            }
        }

        // Checks if the entity is dead
        if (isEnded()) {
            // Plays death sound and display the death text
            if (this.getDeathSound() != null) {
                Game.playSound(this, this.getDeathSound(), 65);
            }
            TextUtil.display(this,this.getName() + " has died! %n");

            // We use remove() and not annihilate()
            // Reason: status instances may last longer than the entity that inflicted them (we still need their UUID)
            this.remove();

            // Temporary level up mechanic
            if (source instanceof Entity entity) {
                int random = new Random().nextInt(1, Math.max(2, entity.getLevel() - this.getLevel()));
                if (random == 1) {
                    entity.addLevel(1);
                }
            }
        }
    }

    // Main healing function
    @Override
    public void heal(Interactable source, float amount) {
        if (this.isEnded()) {
            return;
        }

        // Frenzied reduces outgoing healing
        if (source != null && source.hasSpecifiedStatus(new FrenziedStatus())) {
            amount = amount * 0.75f;
        }

        Game.playSound(this.getPos(), Sound.HEAL, 65);

        // We display the healing message
        String sourceName = "";
        if (source != null) {
            sourceName = source.getDisplayName();
        }
        ActionFeedback.HEAL.printFeedback(this.getDisplayName(), sourceName,
                TextUtil.setColor(new DecimalFormat("##.##").format(amount), Color.orange));

        // We add the healing to the entity, capping out at its maximum health
        health = Math.min(this.getMaxHealth(), health + amount);
    }

    // Checks if the entity is dead
    @Override
    public boolean isEnded() {

        return health <= 0;
    }

    // Adds a level to the entity, and half resets their attributes for the level increase to take effect
    public void addLevel(int level) {
        if (!isEnded()) {
            int newLevel = this.attributes.levelAttribute + level;
            this.attributes = new Attributes()
                    .level(newLevel)
                    .damageType(this.attributes.damageTypeAttribute)
                    .ability(this.attributes.abilityAttribute);
            this.attributes.initialize();

            Game.playSound(this, Sound.HEAL, 65);
            TextUtil.display(this, "%s grows stronger... %n", this.getName());
        }
    }

    // Ticks every item that's either equipped or held
    void itemTurn() {
            List<ItemEquipable> itemEquipables = this.getEquippedItems();

            for (ItemEquipable item : itemEquipables)
            {
                item.turn();
            }
            if (this.getHeldItem() != null) {
                this.getHeldItem().turn();
            }
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

    // Gets the entity health
    public float getHealth() {

        return this.health;
    }

    // Gets the entity max health
    public int getMaxHealth() {
        int amount;
        amount = attributes.maxHealthAttribute;

        amount += (int) getModifierTotal(ModifierType.MAX_HEALTH);

        return amount;
    }

    // Gets the entity's armor
    public int getArmor() {
        int amount = attributes.armorAttribute;

        amount += (int) getModifierTotal(ModifierType.ARMOR);

        return amount;
    }

    // Gets the entity's damage
    public float getDamage() {

        float amount = this.attributes.damageAttribute;

        if (this.hasHeldItem()) {
            amount += this.getHeldItem().getDamage();
        }

        amount += getModifierTotal(ModifierType.DAMAGE);

        return amount;
    }

    public void setDamage(float amount) {
        attributes.damageAttribute = amount;
    }

    // Gets the entity's damage type
    public DamageType getDamageType() {
        if (hasHeldItem()) {
            return getHeldItem().getDamageType();
        }
        return attributes.damageTypeAttribute;
    }


    // Checks of the entity is an enemy (against the player)
    public boolean isEnemy() {

        return enemy;
    }

    // Sets the entity to an enemy
    public void setEnemy(boolean enemy) {

        this.enemy = enemy;
    }


    public boolean isAllied(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (this.isEnemy()) {
            return entity.isEnemy();
        } else {
            return !entity.isEnemy();
        }
    }

    // Gets the level of the entity
    public int getLevel() {

        return attributes.levelAttribute;
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
        if (target == null) {
            return;
        }
        this.target = target.getID();
    }

    // Checks if the entity has a target
    public boolean hasTarget() {

        return getTarget() != null && !getTarget().isEnded();
    }

    // Gets the formatted display name of the entity
    @Override
    public String getDisplayName() {

        return getName() + statusDisplay();
    }

    // Formatted level display of the entity
    public String levelDisplay() {

        return "[" + attributes.levelAttribute + "]";
    }


    // Displays status brackets next to the entity name, used in getDisplayName()
    public String statusDisplay() {
        String display = "";
        String space = ", ";

        if (!getStatuses().isEmpty()) {
            for (Status.Instance statusInstance : getStatuses()) {
                if (getStatuses().getLast() == statusInstance) {
                    space = "";
                }
                String statusName = statusInstance.getStatus().getName();
                statusName = statusInstance.getStatus().isHarmful() ? TextUtil.setColor(statusName, Color.red) : TextUtil.setColor(statusName, Color.green);
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

        ActionFeedback.ABILITY.printFeedback(sourceEntity.getDisplayName(),
                        TextUtil.setColor(sourceEntity.getAbility().getName(), Color.cyan),
                        this.getDisplayName());

        return sourceEntity.getAbility().useAbility(sourceEntity, this);
    }

    @Override
    public boolean receiveResist(Interactable source) {
        Status.Instance instance1 = null;
        for (Status.Instance instance : getStatuses()) {
            if (new Random().nextInt(instance.getAmplifier() + 1) == 1) {
                instance1 = instance;
                break;
            }
        }

        if (instance1 == null) {
            return false;
        }

        removeStatus(instance1.getStatus());
        TextUtil.display(this,"%s resisted and lost %s. %n", source.getName(), instance1.getStatus().getName());
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

    public void removeEquippedItems() {
        equippedItems.clear();
    }

    public void addEquippedItem(ItemEquipable item) {
        item.setOwner(this);
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

    public void setInventory(List<Item> newInventory) {
        this.inventory.clear();

        if (!newInventory.isEmpty()) {
            for (Item item : newInventory) {
                addInventoryItem(item);
            }
        }
    }

    // Adds an item to the inventory
    public void addInventoryItem(Item item) {
        item.setOwner(this);
        inventory.add(item.getID());
        if (item.getPos() != null) {
            item.remove();
        }
    }

    // Removes an item from the inventory
    public void removeFromInventory(Item item) {
        inventory.remove(item.getID());
    }

    // Helper function to remove all items on a given entity
    public void removeAllItems() {
        inventory = new ArrayList<>();
        heldItem = null;
        equippedItems = new ArrayList<>();
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
    public void setHeldItem(@Nullable ItemHoldable item) {
        if (item == null) {
            heldItem = null;
        } else {
            item.setOwner(this);
            heldItem = item.getID();
        }
    }

    // Removes an item from holdable or equipable
    public boolean removeEquippedItem(Item item) {
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

    @Nullable
    public Sound getDeathSound() {
        return null;
    }

    public float getModifierTotal(ModifierType modifierType) {
        float amount = 0;

        amount += Modifier.calculate(this.getModifiers(), modifierType);

        if (this.hasHeldItem()) {
           amount += Modifier.calculate(getHeldItem().getModifiers(), modifierType);
        }

        for (ItemEquipable itemEquipable : getEquippedItems()) {
            amount += Modifier.calculate(itemEquipable.getModifiers(), modifierType);
        }

        return amount;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    public static void spawn(Entity entity, boolean enemy) {
        Entity.spawn(entity, entity.getPos(), enemy);
    }

    public static void spawn(Entity entity, Position position, boolean enemy) {
        entity.setEnemy(enemy);
        Interactable.spawn(entity, position);
    }

    // This class exists entirely because im a lazy piece of shit.
    // It lets me dynamically change the attributes of an entity both in its class and when instantiated.
    // I kinda took "inspiration" from Minecraft for this one
    public static class Attributes {
        private int levelAttribute;
        private int maxHealthAttribute;
        private float damageAttribute;
        private int armorAttribute;
        private DamageType damageTypeAttribute;
        private Ability abilityAttribute;

        public Attributes() {
            this.levelAttribute = -1;
            this.maxHealthAttribute = -1;
            this.damageAttribute = -1;
            this.armorAttribute = -1;
            this.damageTypeAttribute = null;
            this.abilityAttribute = null;
        }

        public void initialize() {
            if (levelAttribute == -1) {levelAttribute = 1;}
            if (maxHealthAttribute == -1) {maxHealthAttribute = 10 * levelAttribute;}
            if (damageAttribute == -1) {
                damageAttribute = levelAttribute;}
            if (armorAttribute == -1) {armorAttribute = levelAttribute;}
            if (damageTypeAttribute == null) {damageTypeAttribute = BluntDamage.get;}
            if (abilityAttribute == null) {abilityAttribute = RageAbility.get;}
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
            if (damageAttribute == -1) {
                damageAttribute = baseDamage;}
            return this;
        }
        public Attributes armor(int armor) {
            if (armorAttribute == -1) {armorAttribute = armor;}
            return this;
        }
        public Attributes damageType(DamageType damageType) {
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

        public float getDamage() {return damageAttribute;}

        public int getArmor() {return armorAttribute;}

        public DamageType getDamageType() {return damageTypeAttribute;}

        public Ability getAbility() {return abilityAttribute;}

        public Attributes copyOf(Entity entity) {
            Attributes attributes = entity.attributes;
            this.levelAttribute = attributes.levelAttribute;
            this.maxHealthAttribute = attributes.maxHealthAttribute;
            this.damageAttribute = attributes.damageAttribute;
            this.armorAttribute = attributes.armorAttribute;
            this.damageTypeAttribute = attributes.damageTypeAttribute;
            this.abilityAttribute = attributes.abilityAttribute;
            return this;
        }
    }
}
