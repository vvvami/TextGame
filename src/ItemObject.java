public class ItemObject extends ObjectInteractable {

    private boolean reusable;
    private int damageAmount;
    private DamageType damageType;
    private boolean equipped;

    public ItemObject(String name, String description, boolean reusable, int damageAmount, DamageType damageType) {
        super(name, description);
        this.reusable = reusable;
        this.damageAmount = damageAmount;
        this.damageType = damageType;
    }

    public final ItemObject sword = new ItemObject
            ("Sword", "a rusty sword", true, 10, DamageType.sharp);


    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public void setDamageAmount(int damageAmount) {
        this.damageAmount = damageAmount;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public boolean isReusable() {
        return reusable;
    }
}
