package net.vami.game.interactables.items.attunement;

import net.vami.game.interactables.items.BreakableItem;
import net.vami.game.interactables.items.Item;

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
