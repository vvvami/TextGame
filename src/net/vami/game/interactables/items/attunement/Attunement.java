package net.vami.game.interactables.items.attunement;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.items.Item;

@JsonAdapter(AttunementAdapter.class)
public interface Attunement {
    String getName();
    boolean isCurse();

    default boolean applyCondition(Item item) {
        return true;
    }

    default boolean removeCondition(Item item) {
        return !isCurse();
    }

    // When the attunement is applied
    default void onApply(Item item) {

    }

    // When the attunement is removed
    default void onRemove(Item item) {

    }

    // When the item with the attunement hits
    default void onHit(Item item, Interactable source, Entity target, float amount, DamageType damageType) {

    }

    // On item turn
    default void onTurn(Item item) {

    }

    // When the item loses durability
    default void onItemHurt(Item item, int amount) {

    }

    // If the item is of UseableItem, this will be triggered on use
    default void onUse(Item item, Entity source) {

    }
}
