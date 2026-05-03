package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.item.ItemBreakable;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;

public class TearsOfArthuurosItem extends Item implements ItemUseable, ItemBreakable {
    public TearsOfArthuurosItem() {
        super("Tears of Arthuuros");
    }

    @Override
    public void onUse(Interactable source, ItemInstance item) {
        source.heal(null, 10);
        item.hurt(1);
    }

    @Override
    public boolean useCondition(ItemInstance item) {
        return item.getOwner().getHealth() < item.getOwner().getAttributes().getMaxHealth();
    }

    @Override
    public String failMessage(ItemInstance item) {
        return item.getOwner().getName() + " has no more health to restore.";
    }

    @Override
    public int maxDurability() {
        return 1;
    }
}
