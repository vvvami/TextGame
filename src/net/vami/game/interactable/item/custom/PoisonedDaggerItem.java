package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;
import net.vami.game.interactable.item.ItemHoldable;

public class PoisonedDaggerItem extends ItemHoldable {
    public PoisonedDaggerItem(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(2)
                .damageType(DamageTypes.SHARP));
    }

    public PoisonedDaggerItem(Attributes attributes) {
        this("Poisoned Dagger", attributes);
    }

    public PoisonedDaggerItem() {
        this(new Attributes());
    }

    @Override
    public void onHit(Interactable owner, Interactable target, DamageType damageType, float amount) {
        target.addStatus(new Status.Instance(
                Statuses.POISONED,
                1,
                1,
                owner));
    }
}
