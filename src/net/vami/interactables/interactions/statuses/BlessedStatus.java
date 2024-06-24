package net.vami.interactables.interactions.statuses;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.Status;

public class BlessedStatus extends Status {
    public static final BlessedStatus STATUS = new BlessedStatus("Blessed", false, true, false);

    protected BlessedStatus(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful) {
        super(name, stacksAmplifier, stacksDuration, harmful);
    }

    @Override
    protected void onApply(Entity target, Entity source) {
        if (target.hasSpecifiedStatus(WoundedStatus.STATUS)) {
            target.removeStatus(WoundedStatus.STATUS);
        }
    }

    @Override
    protected void turn(Entity target, Entity source) {
        target.heal(source, (float) target.getStatusInstance(this).getAmplifier() / 2);
    }

}
