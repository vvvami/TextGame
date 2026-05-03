package net.vami.game.interactable.item;

import net.vami.game.interactable.Interactable;

public interface ItemUseable {

    default boolean useCondition(ItemInstance item) {
        return true;
    }

    void onUse(Interactable source, ItemInstance item);

    default String failMessage(ItemInstance item) {
        return "";
    }

}
