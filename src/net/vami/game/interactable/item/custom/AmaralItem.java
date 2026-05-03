package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
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
    public void onUse(Interactable source) {
        Game.playSound(source, this.getDamageType().getSound(), 65);

        Game.display(source,"%s attacks their surroundings with %s! %n",
                source.getName(), this.getDisplayName());

        for (Entity entity : Node.findNode(source.getPos()).getEntities()) {
            if (!(entity == source)) {
                entity.hurt(source, this.getDamage(), this.getDamageType());
            }
        }
        this.hurt(5);
    }

}
