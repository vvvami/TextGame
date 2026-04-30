package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.ItemBreakable;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.ItemUseable;
import net.vami.game.interactable.item.attunement.AttunableItem;
import net.vami.game.world.Node;

public class AmaralItem extends ItemHoldable implements ItemBreakable, ItemUseable, AttunableItem {
    public AmaralItem(String name, Attributes attributes) {
        super(name, attributes
                .damageType(DamageTypes.FIRE)
                .baseDamage(15));
    }

    public AmaralItem(Attributes attributes) {
        this("Amaral", attributes);
    }

    public AmaralItem() {
        this(new Attributes());
    }

    @Override
    public int maxDurability() {
        return 100;
    }

    @Override
    public void onUse() {
        Game.playSound(this.getOwner(), this.getDamageType().getSound(), 65);

        Game.display(this.getOwner(),"%s attacks their surroundings with %s! %n",
                this.getOwner().getName(), this.getDisplayName());

        for (Entity entity : Node.findNode(this.getOwner().getPos()).getEntities()) {
            if (!(entity == this.getOwner())) {
                entity.hurt(this.getOwner(), this.getDamage(), this.getDamageType());
            }
        }
        this.hurt(5);
    }

}
