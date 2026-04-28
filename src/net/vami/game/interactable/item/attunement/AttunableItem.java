package net.vami.game.interactable.item.attunement;

public interface AttunableItem {

    default boolean canAttune() {
        return true;
    }

}
