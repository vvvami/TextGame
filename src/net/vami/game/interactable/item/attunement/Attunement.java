package net.vami.game.interactable.item.attunement;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.interactable.Hoverable;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.loot.Rollable;

@JsonAdapter(AttunementAdapter.class)
public abstract class Attunement implements Rollable, Hoverable {
    public abstract String getName();
    public abstract boolean isCurse();

    public boolean applyCondition(ItemInstance item) {
        return true;
    }

    public boolean removeCondition(ItemInstance item) {
        return !isCurse();
    }

    // When the attunement is applied
    public void onApply(ItemInstance item) {

    }

    // When the attunement is removed
    public void onRemove(ItemInstance item) {

    }

    // When the item with the attunement hits
    public void onHit(ItemInstance item, Interactable source, Entity target, float amount, DamageType damageType) {

    }

    // On item turn
    public void onTurn(ItemInstance item) {

    }

    // When the item loses durability
    public void onItemHurt(ItemInstance item, int amount) {

    }

    // If the item is of UseableItem, this will be triggered on use
    public void onUse(ItemInstance item, Entity source) {

    }

    @Override
    public HoverInfo getHoverInfo() {
        return new HoverInfo(getDisplayName(),"Applies an effect to an item.");
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
}
