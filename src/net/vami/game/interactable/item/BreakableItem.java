package net.vami.game.interactable.item;

public interface BreakableItem {
    int maxDurability();
    default boolean damageOnHit() {
        return true;
    }
}
