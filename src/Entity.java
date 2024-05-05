import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Entity extends ObjectInteractable {

    private static int identifier;
    private final int ID = identifier;
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
    private Ability ability;
    private Entity target;

    public Entity(String name, int level, int maxHealth, float baseDamage,
                  int armor, DamageType DEFAULT_DAMAGETYPE, boolean enemy, Ability ability) {
        super(name, null);
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
        String stringAmount = Main.ANSI_YELLOW + amount + Main.ANSI_RESET;
        if (!isAlive()) {
            return;
        }
        health = Math.min(maxHealth, health + amount);
        System.out.printf("%s was healed by %s for %s health! %n", getDisplayName(), source.getDisplayName(),
                 stringAmount);
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
            statusEffects.removeIf(statusInstance -> statusInstance.getStatus() == status);
    }

    public void entityTurn() {
        if (hasStatus()) {
            List<StatusInstance> removeList = new ArrayList<>();
            for (StatusInstance statusInstance : statusEffects) {
                statusInstance.statusTurn();
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
                display = display + statusInstance.getStatus()
                        .getName() + space;
            }
            display = " (" + display + ")";
        }
        return display;
    }


}
