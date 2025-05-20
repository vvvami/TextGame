package net.vami.game.interactables.items;

public interface BreakableItem {
    int maxDurability();
    default boolean damageOnHit() {
        return true;
    }
}
