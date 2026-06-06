package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.ItemBreakable;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;
import net.vami.game.interactable.item.attunement.ItemAttunable;

public class KnivesOfQalathItem extends ItemHoldable implements ItemBreakable, ItemAttunable, ItemUseable {
    private transient boolean doubleHit;

    public KnivesOfQalathItem(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(5)
                .damageType(DamageTypes.SHARP));
    }

    public KnivesOfQalathItem(Attributes attributes) {
        this("Knives of Qalath", attributes);
    }

    public KnivesOfQalathItem() {
        this(new Attributes());
    }

    @Override
    public void onHit(Interactable owner, Interactable target, DamageType damageType, float amount) {
        if (doubleHit) {
            doubleHit = false;
        } else {
            doubleHit = true;
            target.hurt(owner, amount, damageType);
        }
    }

    @Override
    public int maxDurability() {
        return 1450;
    }


    @Override
    public boolean onHold(ItemInstance item) {
        doubleHit = false;
        return super.onHold(item);
    }

    @Override
    public void onUse(Interactable source, ItemInstance item) {

    }
}
