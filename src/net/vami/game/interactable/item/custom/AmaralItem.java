package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.display.Segmental;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.ItemBreakable;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;
import net.vami.game.interactable.item.attunement.ItemAttunable;
import net.vami.game.world.Node;

public class AmaralItem extends ItemHoldable implements ItemBreakable, ItemUseable, ItemAttunable {
    public AmaralItem(String name, Attributes attributes) {
        super(name, attributes
                .damageType(DamageTypes.FIRE)
                .baseDamage(5));
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
    public void onUse(Interactable source, ItemInstance item) {
//        Display.playSound(source, this.getDamageType().getSound(), 65);
//
//        Display.showText(source,"%s attacks their surroundings with %s! %n",
//                source, this);
//
        Display.display(source,
                new Segmental("%s attacks their surroundings with %s! %n",
                        source, this),
                this.getDamageType().getSound(), 65);

        for (Entity entity : Node.findNode(source.getPos()).getEntities()) {
            if (!(entity == source)) {
                entity.hurt(source, this.getDamage() * 2, this.getDamageType());
                item.hurt(1);
            }
        }
    }

}
