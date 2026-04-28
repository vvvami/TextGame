package net.vami.game.interactable.item;

public interface UseableItem {

    default boolean useCondition() {
        return true;
    }

    void onUse();

    default String failMessage() {
        return "";
    }

}
