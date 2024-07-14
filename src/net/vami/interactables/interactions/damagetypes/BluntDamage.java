package net.vami.interactables.interactions.damagetypes;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.statuses.CrippledStatus;
import net.vami.interactables.interactions.statuses.Status;

public class BluntDamage implements DamageType {

    @Override
    public void onHit(Entity target, Entity source, float amount) {
        if (!target.hasSpecifiedStatus(new CrippledStatus())) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        new CrippledStatus(), (int) amount / 2, (int) amount, source);
            }
        }
    }

    @Override
    public String getName() {
        return "Blunt";
    }
}
