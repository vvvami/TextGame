package net.vami.game.interactable.item;

public interface ItemBreakable {
    int maxDurability();
    default boolean damageOnHit() {
        return true;
    }
}
