package net.vami.game.interactables.items.custom;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;
import net.vami.game.interactables.items.BreakableItem;
import net.vami.game.interactables.items.ItemHoldable;
import net.vami.game.interactables.items.UseableItem;
import net.vami.game.interactables.items.attunement.AttunableItem;

public class KnivesOfQalath extends ItemHoldable implements BreakableItem, AttunableItem, UseableItem {
    private boolean doubleHit;

    public KnivesOfQalath(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(5)
                .damageType(new SharpDamage()));
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
}
