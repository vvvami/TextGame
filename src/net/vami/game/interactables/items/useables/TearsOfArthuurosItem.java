package net.vami.game.interactables.items.useables;

import net.vami.game.interactables.items.BreakableItem;
import net.vami.game.interactables.items.Item;

public class TearsOfArthuurosItem extends Item implements UseableItem, BreakableItem {
    public TearsOfArthuurosItem(String name) {
        super(name);
    }

    @Override
    public void onUse() {
        this.getOwner().heal(null, 10);
        this.hurt(1);
    }

    @Override
    public boolean useCondition() {
        return this.getOwner().getHealth() < this.getOwner().getAttributes().getMaxHealth();
    }

    @Override
    public String failMessage() {
        return this.getOwner().getName() + " has no more health to restore.";
    }

    @Override
    public int maxDurability() {
        return 1;
    }
}
