package net.vami.game.interactable.item;

public interface ItemUseable {

    default boolean useCondition() {
        return true;
    }

    void onUse();

    default String failMessage() {
        return "";
    }

}
