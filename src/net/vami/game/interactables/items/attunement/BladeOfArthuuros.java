package net.vami.game.interactables.items.attunement;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.BurningStatus;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.items.ItemHoldable;

public class BladeOfArthuuros extends ItemHoldable implements AttunableItem {
    public BladeOfArthuuros(String name, Attributes attributes) {
        super(name, attributes);
    }

    @Override
    public void onHit(Interactable owner, Interactable target, DamageType damageType, float amount) {
        target.addStatus(new Status.Instance(BurningStatus.get, 1, 2, owner));
    }
}
