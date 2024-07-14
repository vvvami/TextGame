package net.vami.interactables.interactions.damagetypes;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.statuses.FrozenStatus;
import net.vami.interactables.interactions.statuses.Status;

public class IceDamage implements DamageType {

    @Override
    public void onHit(Entity target, Entity source, float amount) {
        if (!target.hasSpecifiedStatus(new FrozenStatus())) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        new FrozenStatus(), (int) amount, (int) amount, source);
            }
        }
    }

    @Override
    public String getName() {
        return "Ice";
    }
}
