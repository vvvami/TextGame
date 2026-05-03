package net.vami.game.interactable.item.attunement;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;

@JsonAdapter(AttunementAdapter.class)
public interface Attunement {
    String getName();
    boolean isCurse();

    default boolean applyCondition(ItemInstance item) {
        return true;
    }

    default boolean removeCondition(ItemInstance item) {
        return !isCurse();
    }

    // When the attunement is applied
    default void onApply(ItemInstance item) {

    }

    // When the attunement is removed
    default void onRemove(ItemInstance item) {

    }

    // When the item with the attunement hits
    default void onHit(ItemInstance item, Interactable source, Entity target, float amount, DamageType damageType) {

    }

    // On item turn
    default void onTurn(ItemInstance item) {

    }

    // When the item loses durability
    default void onItemHurt(ItemInstance item, int amount) {

    }

    // If the item is of UseableItem, this will be triggered on use
    default void onUse(ItemInstance item, Entity source) {

    }
}
