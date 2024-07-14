package net.vami.interactables.interactions.damagetypes;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.statuses.Status;
import net.vami.interactables.interactions.statuses.WoundedStatus;

public class SharpDamage implements DamageType {

    @Override
    public void onHit(Entity target, Entity source, float amount) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        new WoundedStatus(), (int) amount, (int) amount, source);
            }
    }

    @Override
    public String getName() {
        return "Sharp";
    }
}
