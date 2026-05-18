package net.vami.game.display.panel;

public class HoverComponent {
    private final String category;

    protected HoverComponent(String category) {
        this.category = category;

    }

    public String get() {
        return category + ": ";
    }

    public static final HoverComponent DAMAGE = new HoverComponent("Damage");
    public static final HoverComponent HEALTH = new HoverComponent("Health");
    public static final HoverComponent LEVEL = new HoverComponent("Level");
    public static final HoverComponent ABILITY = new HoverComponent("Ability");
    public static final HoverComponent HELD_ITEM = new HoverComponent("Item");
    public static final HoverComponent ARMOR = new HoverComponent("Armor");

}
