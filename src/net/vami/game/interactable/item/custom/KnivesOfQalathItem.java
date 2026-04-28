package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.BreakableItem;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.UseableItem;
import net.vami.game.interactable.item.attunement.AttunableItem;

public class KnivesOfQalathItem extends ItemHoldable implements BreakableItem, AttunableItem, UseableItem {
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
    public void onUse() {

    }

    @Override
    public boolean onEquip() {
        doubleHit = false;
        return super.onEquip();
    }
}
