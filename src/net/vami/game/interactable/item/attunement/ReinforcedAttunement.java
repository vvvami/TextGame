package net.vami.game.interactable.item.attunement;

import net.vami.game.interactable.item.BreakableItem;
import net.vami.game.interactable.item.Item;

public class ReinforcedAttunement implements Attunement {

    @Override
    public String getName() {
        return "Reinforced";
    }

    @Override
    public boolean isCurse() {
        return false;
    }

    @Override
    public void onItemHurt(Item item, int amount) {
        item.setDurability(item.getDurability() +
                (int) Math.floor((double) amount / 2));
    }

    @Override
    public boolean applyCondition(Item item) {
        return item instanceof BreakableItem;
    }
}
