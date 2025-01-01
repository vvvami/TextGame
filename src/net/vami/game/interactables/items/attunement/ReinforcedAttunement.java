package net.vami.game.interactables.items.attunement;

import net.vami.game.interactables.items.Item;

public class ReinforcedAttunement implements Attunement {

    @Override
    public String name() {
        return "Reinforced";
    }

    @Override
    public boolean curse() {
        return false;
    }

    @Override
    public void onItemHurt(Item item, int amount) {
        item.setDurability(item.getDurability() +
                (int) Math.floor((double) amount / 2));
    }
}