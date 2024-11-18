package net.vami.game.interactables.items.attunement;

public interface AttunableItem {

    default boolean canAttune() {
        return true;
    }

}
