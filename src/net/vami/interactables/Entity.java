package net.vami.interactables;
import net.vami.game.*;
import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Entity extends Interactable {


    private int maxHealth;
    private float health;
    private float baseDamage;
    private int armor;
    private final DamageType DEFAULT_DAMAGETYPE;
    private int level;
    private List<DamageType> weaknesses;
    private List<DamageType> resistances;
    private List<StatusInstance> statusEffects = new ArrayList<>();
    private List<Item> inventory = new ArrayList<>();
    private Item equippedItem;
    private boolean enemy;
    private Ability ability;
    private Entity target;


    public Entity(String name, Position position, int level, int maxHealth, float baseDamage,
                  int armor, DamageType DEFAULT_DAMAGETYPE, boolean enemy, Ability ability) {
        super(name, null, position);
        this.level = level;
        this.maxHealth = maxHealth;
        this.baseDamage = baseDamage;
        this.armor = armor;
        health = this.maxHealth;
        this.DEFAULT_DAMAGETYPE = DEFAULT_DAMAGETYPE;
        this.ability = ability;
        this.enemy = enemy;

    }

    // net.vami.game.Main damage function
    public void hurt(Entity source, float amount, DamageType damageType) {

        if (armor > 0) {
            amount = (float) (amount / Math.sqrt(armor));
        }
        health -= amount;
        System.out.printf("%s suffered %s damage! %n", getDisplayName(), Main.ANSI_YELLOW
                + new DecimalFormat("##.##").format(amount)
                + Main.ANSI_RESET + " " + damageType.getName());
    }

    // net.vami.game.Main healing function
    public void heal(Entity source, float amount) {
        String stringAmount = Main.ANSI_YELLOW + amount + Main.ANSI_RESET;
        if (!isEnded()) {
            return;
        }
        health = Math.min(maxHealth, health + amount);
        System.out.printf("%s was healed by %s for %s health! %n", getDisplayName(), source.getDisplayName(),
                 stringAmount);
    }

    // Checks if the entity is alive
    @Override
    public boolean isEnded() {
        return health > 0;
    }

    // Adds a status effect. Stacks the status according to the status' parameters defined in the net.vami.interactables.Status enum
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

    public float getHealth() {
        return this.health;
    }


    public float getBaseDamage() {
        return baseDamage;
    }

    public Item getEquippedItem() {
        return equippedItem;
    }

    public boolean hasEquippedItem() {
        return !(getEquippedItem() == null);
    }

    public void setEquippedItem(Item equippedItem) {
        this.equippedItem = equippedItem;
    }

    public DamageType getDefaultDamageType() {
        return DEFAULT_DAMAGETYPE;
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


    @Override
    protected boolean receiveAttack(Interactable source) {
        if (!(source instanceof Entity)) {
            return false;
        }
        Entity entitySource = (Entity) source;
        float damage = entitySource.getBaseDamage();
        DamageType type = entitySource.getDefaultDamageType();

        if (entitySource.hasEquippedItem()) {
            damage += entitySource.getEquippedItem().getDamageAmount();
            type = entitySource.getEquippedItem().getDamageType();
        }

        System.out.printf("%s was hit by %s! %n", getDisplayName(), entitySource.getDisplayName());
        hurt(entitySource, damage, type);
        return true;
    }

    @Override
    protected boolean receiveAbility(Interactable source) {
        if (!(source instanceof Entity)) {
            return false;
        }
        Entity entitySource = (Entity) source;
        float damage = entitySource.getBaseDamage();
        DamageType type = entitySource.getDefaultDamageType();

        if (entitySource.hasEquippedItem()) {
            damage += entitySource.getEquippedItem().getDamageAmount();
            type = entitySource.getEquippedItem().getDamageType();
        }

        System.out.printf("%s was hit by %s! %n", getDisplayName(), entitySource.getDisplayName());
        hurt(entitySource, damage, type);
        return true;
    }

}
