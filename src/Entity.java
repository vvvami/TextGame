import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Entity {

    private static int identifier;
    private final int ID = identifier;
    private final String NAME;
    private int maxHealth;
    private float health;
    private float baseDamage;
    private int armor;
    private final DamageType DEFAULT_DAMAGETYPE;
    private int level;
    private List<DamageType> weaknesses;
    private List<StatusInstance> statusEffects = new ArrayList<>();
    private List<ItemObject> inventory = new ArrayList<>();
    private ItemObject equippedItem;
    private Action action;
    private boolean enemy;
    private String description;
    private Ability ability;
    private Entity target;

    public Entity(String NAME, int level, int maxHealth, float baseDamage,
                  int armor, DamageType DEFAULT_DAMAGETYPE, boolean enemy, Ability ability) {

        this.NAME = NAME;
        this.level = level;
        this.maxHealth = maxHealth;
        this.baseDamage = baseDamage;
        this.armor = armor;
        health = this.maxHealth;
        this.DEFAULT_DAMAGETYPE = DEFAULT_DAMAGETYPE;
        this.ability = ability;
        this.enemy = enemy;
        identifier++;
    }

    // Main damage function
    public void hurt(Entity source, float amount, DamageType damageType) {

        if (armor > 0) {
            amount = (float) (amount / Math.sqrt(armor));
        }
        health -= amount;
        System.out.printf("%s suffered %s damage! %n", getDisplayName(), Main.ANSI_YELLOW
                + new DecimalFormat("##.##").format(amount)
                + Main.ANSI_RESET + " " + damageType.getName());
    }

    // Main healing function
    public void heal(Entity source, float amount) {
        if (!isAlive()) {
            return;
        }
        health = Math.min(maxHealth, health + amount);
        System.out.printf("%s was healed by %s for %s health! %n", getDisplayName(), source.getDisplayName(),
                Main.ANSI_YELLOW + new DecimalFormat("##.##").format(amount) + Main.ANSI_RESET);
    }

    // Checks if the entity is alive
    public boolean isAlive() {
        return health > 0;
    }

    // Adds a status effect. Stacks the status according to the status' parameters defined in the Status enum
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
        status.setTarget(this);
        statusEffects.add(status);
    }

    // Remove a status. Removing a status means removing an entire instance of that status, because Statuses stack
    public void removeStatus(Status status) {
        Iterator<StatusInstance> statusInstanceIterator = statusEffects.iterator();
        while (statusInstanceIterator.hasNext()) {
            StatusInstance statusInstance = statusInstanceIterator.next();
            if (status.equals(statusInstance.getStatus())) {
                statusInstanceIterator.remove();
            }
        }
    }

    public void entityTurn() {
        if (hasStatus()) {
            Iterator<StatusInstance> statusIterator = statusEffects.iterator();
            while (statusIterator.hasNext()) {
                StatusInstance nextStatus = statusIterator.next();
                nextStatus.statusTurn();
                if (nextStatus.getDuration() <= 0) {
                    statusIterator.remove();
                    System.out.printf("%s is no longer %s. %n", getDisplayName(), nextStatus.getStatus().getName());
                }
            }
        }

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

    public int getID() {
        return ID;
    }

    public float getHealth() {
        return this.health;
    }


    public float getBaseDamage() {
        return baseDamage;
    }


    public String getName() {
        return NAME;
    }

    public ItemObject getEquippedItem() {
        return equippedItem;
    }

    public boolean hasEquippedItem() {
        return !(getEquippedItem() == null);
    }

    public void setEquippedItem(ItemObject equippedItem) {
        this.equippedItem = equippedItem;
    }

    public DamageType getDefaultDamageType() {
        return DEFAULT_DAMAGETYPE;
    }


    public void setAction(Action action) {
        this.action = action;
    }


    public Action getAction() {
        return action;
    }

    // Checks of the entity is an enemy (against the player)
    public boolean isEnemy() {
        return enemy;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDisplayName() {
        return NAME + statusDisplay();
    }

    public String levelDisplay() {
        return "[" + level + "]";
    }

    public String statusDisplay() {
        String color = "";
        String display = "";
        String space = ", ";

        if (!statusEffects.isEmpty()) {
            for (StatusInstance statusInstance : statusEffects) {
                if (statusInstance.getStatus().isHarmful()) {
                 color = Main.ANSI_RED;
                }
                else {
                    color = Main.ANSI_GREEN;
                }
                if (statusEffects.getLast() == statusInstance) {
                    space = "";
                }
                display = display + color + statusInstance.getStatus()
                        .getName() + Main.ANSI_RESET + space;
            }
            display = " (" + display + ")";
        }
        return display;
    }


}
