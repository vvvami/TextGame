package net.vami.interactables.interactions.statuses;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.DamageType;
import net.vami.interactables.interactions.Status;

public class WoundedStatus extends Status {
    public static final WoundedStatus STATUS = new WoundedStatus("Wounded", true, true, true);

    protected WoundedStatus(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful) {
        super(name, stacksAmplifier, stacksDuration, harmful);
    }

    @Override
    protected void turn(Entity target, Entity source) {
        target.hurt(source, target.getStatusInstance(this).getAmplifier(), DamageType.BLEED);
    }
}
