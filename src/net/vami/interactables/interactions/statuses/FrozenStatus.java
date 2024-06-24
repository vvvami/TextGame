package net.vami.interactables.interactions.statuses;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.DamageType;
import net.vami.interactables.interactions.Status;

public class FrozenStatus extends Status {
    public static final FrozenStatus STATUS = new FrozenStatus("Frozen", true, true, true);

    protected FrozenStatus(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful) {
        super(name, stacksAmplifier, stacksDuration, harmful);
    }

    @Override
    protected void turn(Entity target, Entity source) {
        target.hurt(source, (float) target.getStatusInstance(this).getAmplifier() / 2, DamageType.ICE);
    }
}
