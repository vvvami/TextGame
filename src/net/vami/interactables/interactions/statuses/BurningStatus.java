package net.vami.interactables.interactions.statuses;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.DamageType;
import net.vami.interactables.interactions.Status;

public class BurningStatus extends Status {
    public static final BurningStatus STATUS = new BurningStatus("Burning", false, true, true);

    protected BurningStatus(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful) {
        super(name, stacksAmplifier, stacksDuration, harmful);
    }

    @Override
    protected void turn(Entity target, Entity source) {
        target.hurt(source, target.getStatusInstance(this).getAmplifier(), DamageType.FIRE);
    }
}
