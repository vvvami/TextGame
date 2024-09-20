package net.vami.game.interactables.items.useables;

public interface UseableItem {

    default boolean useCondition() {
        return true;
    }

    void onUse();

    default String failMessage() {
        return "";
    }

}
