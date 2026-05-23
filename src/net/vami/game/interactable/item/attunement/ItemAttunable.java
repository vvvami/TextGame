package net.vami.game.interactable.item.attunement;

public interface ItemAttunable {

    default boolean canAttune() {
        return true;
    }

}
