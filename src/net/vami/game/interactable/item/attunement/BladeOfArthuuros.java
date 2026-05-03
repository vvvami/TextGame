package net.vami.game.interactable.item.attunement;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;
import net.vami.game.interactable.item.ItemHoldable;

public class BladeOfArthuuros extends ItemHoldable implements AttunableItem {
    public BladeOfArthuuros(String name, Attributes attributes) {
        super(name, attributes);
    }

    public BladeOfArthuuros(Attributes attributes) {
        this("Blade of Arthuuros", attributes);
    }

    public BladeOfArthuuros() {
        this(new Attributes());
    }

    @Override
    public void onHit(Interactable owner, Interactable target, DamageType damageType, float amount) {
        target.addStatus(new Status.Instance(Statuses.BURNING, 1, 2, owner));
    }
}
