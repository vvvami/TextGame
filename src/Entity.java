import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

    private final String name;
    private final int maxHealth;
    private float health;
    private final float baseDamage;
    private int armor;
    private final Damage defaultDamage;
    private List<Damage> weaknesses;
    private List<StatusInstance> statusEffects = new ArrayList<>();
    private List<ItemObject> inventory = new ArrayList<>();
    private ItemObject equippedItem;
    private Action action;
    private boolean enemy;
    
    public Entity(String name, int maxHealth, float baseDamage, int armor, Damage defaultDamage, boolean enemy) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.baseDamage = baseDamage;
        this.armor = armor;
        health = this.maxHealth;
        this.defaultDamage = defaultDamage;
        this.enemy = enemy;
    }

    // Main damage function
    public void hurt(Entity source, float amount, Damage damage) {

        if (armor > 0) {
            amount = (float) (amount / Math.sqrt(armor));
        }

        health -= amount;
        System.out.println(name + " was hit by " + source.name + " for "
                + new DecimalFormat("##.##").format(amount) + " damage!");

    }

    // Main healing function
    public void heal(float amount) {
        if (!isAlive()) {
            return;
        }
        health = Math.min(maxHealth, health + amount);
    }

    // Checks if the entity is alive
    public boolean isAlive() {
        return health > 0;
    }

    // Adds a status effect
    public void addStatus(StatusInstance status) {
        statusEffects.add(status);

    }

    public void removeStatus(Status status) {
        for (StatusInstance statusInstance : statusEffects) {
            if (status.equals(statusInstance.getStatus())) {
                statusEffects.remove(statusInstance);
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

    public boolean hasStatus(Status status) {
        for (StatusInstance statusInstance : statusEffects) {
            if (status.equals(statusInstance.getStatus())) {
                return true;
            }
        }
        return false;
    }

    public List<StatusInstance> getStatuses() {
        return statusEffects;
    }


    public void statusTicker() {

    }


    public float getHealth() {
        return this.health;
    }


    public float getBaseDamage() {
        return baseDamage;
    }


    public String getName() {
        return name;
    }


    public Damage getDefaultDamage() {
        return defaultDamage;
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

}
