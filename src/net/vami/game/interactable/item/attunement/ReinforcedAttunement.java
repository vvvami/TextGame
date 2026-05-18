package net.vami.game.interactable.item.attunement;

import net.vami.game.interactable.item.ItemBreakable;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;

public class ReinforcedAttunement extends Attunement {

    @Override
    public String getName() {
        return "Reinforced";
    }

    @Override
    public boolean isCurse() {
        return false;
    }

    @Override
    public void onItemHurt(ItemInstance item, int amount) {
        item.setDurability(item.getDurability() +
                (int) Math.floor((double) amount / 2));
    }

    @Override
    public boolean applyCondition(ItemInstance item) {
        return item instanceof ItemBreakable;
    }
}
