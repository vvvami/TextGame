package net.vami.interactables.interactions.damagetypes;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.statuses.IStatus;
import net.vami.interactables.interactions.statuses.WoundedStatus;

public class SharpDamage implements IDamageType {

    @Override
    public void onHit(Entity target, Entity source, float amount) {
            if (amount * Math.random() > 1) {
                new IStatus.Instance(
                        new WoundedStatus(), (int) amount, (int) amount, source);
            }
    }

    @Override
    public String getName() {
        return "Sharp";
    }
}
