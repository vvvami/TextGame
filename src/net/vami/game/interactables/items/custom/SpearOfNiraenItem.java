package net.vami.game.interactables.items.custom;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;
import net.vami.game.interactables.interactions.modifier.Modifier;
import net.vami.game.interactables.interactions.modifier.ModifierType;
import net.vami.game.interactables.items.ItemHoldable;
import net.vami.game.interactables.items.UseableItem;
import net.vami.util.TextUtil;

public class SpearOfNiraenItem extends ItemHoldable implements UseableItem {

    public SpearOfNiraenItem(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(5)
                .damageType(SharpDamage.get));
    }

    public SpearOfNiraenItem(Attributes attributes) {
        this("Spear", attributes);
    }

    public SpearOfNiraenItem() {
        this(new Attributes());
    }

    @Override
    public void onUse() {
            TextUtil.display("%s has thrown %s at %s! %n",
                    this.getOwner().getName(),
                    this.getDisplayName(),
                    this.getOwner().getTarget().getName());
            this.getOwner().getTarget().hurt(this.getOwner(), 10f, this.getDamageType());
            this.receiveDrop(this.getOwner());
        }
}
