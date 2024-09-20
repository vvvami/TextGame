package net.vami.game.interactables.items.holdables;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.items.BreakableItem;
import net.vami.game.interactables.items.useables.UseableItem;
import net.vami.game.world.Node;

public class ExcaliburItem extends ItemHoldable implements BreakableItem, UseableItem {
    public ExcaliburItem(String name, Attributes attributes) {
        super(name, attributes
                .damageType(new FireDamage())
                .baseDamage(15));
    }

    @Override
    public int maxDurability() {
        return 100;
    }

    @Override
    public void onUse() {
        System.out.printf("%s blasts their surroundings with %s! %n", this.getOwner().getName(), this.getDisplayName());
        for (Entity entity : Node.getNodeFromPosition(this.getOwner().getPos()).getEntities()) {
            if (!(entity == this.getOwner())) {
                entity.hurt(this.getOwner(), 10, this.getAttributes().damageTypeAttribute);
            }
        }
        this.hurt(5);
    }
}
