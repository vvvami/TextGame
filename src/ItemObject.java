public class ItemObject extends Object {

    private boolean reusable;
    private String name;
    private int damageAmount;
    private Damage damage;
    private boolean equipped;


    public ItemObject(boolean reusable, String name, int damageAmount, Damage damage) {
        this.name = name;
        this.damageAmount = damageAmount;
        this.damage = damage;
    }

    public final ItemObject sword = new ItemObject(true,"Sword", 5, Damage.sharp);

}
