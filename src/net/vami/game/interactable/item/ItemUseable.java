package net.vami.game.interactable.item;

import net.vami.game.interactable.Interactable;

public interface ItemUseable {

    default boolean useCondition() {
        return true;
    }

    void onUse(Interactable source);

    default String failMessage() {
        return "";
    }

}
