package net.vami.game.interactables.items.custom;

import net.vami.game.Game;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.items.BreakableItem;
import net.vami.game.interactables.items.ItemHoldable;
import net.vami.game.interactables.items.UseableItem;
import net.vami.game.interactables.items.attunement.AttunableItem;
import net.vami.game.world.Node;
import net.vami.util.TextUtil;

public class ExcaliburItem extends ItemHoldable implements BreakableItem, UseableItem, AttunableItem {
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
        Game.playSound(this.getOwner(), this.getDamageType().getSound(), 65);

        TextUtil.display(this.getOwner(),"%s attacks their surroundings with %s! %n",
                this.getOwner().getName(), this.getDisplayName());

        for (Entity entity : Node.findNode(this.getOwner().getPos()).getEntities()) {
            if (!(entity == this.getOwner())) {
                entity.hurt(this.getOwner(), this.getDamage(), this.getDamageType());
            }
        }
        this.hurt(5);
    }

}
