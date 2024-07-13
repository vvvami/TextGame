package net.vami.interactables.interactions.statuses;

import net.vami.interactables.entities.Entity;

public class BlessedStatus implements IStatus {

    @Override
    public void onApply(Entity target, Entity source) {
        if (target != null) {
            if (target.hasSpecifiedStatus(new WoundedStatus())) {
                target.removeStatus(new WoundedStatus());
            }
        }
    }

    @Override
    public void turn(Entity target, Entity source) {
        target.heal(source, (float) target.getStatusInstance(this).getAmplifier() / 2);
    }

    @Override
    public String getName() {
        return "Blessed";
    }

    @Override
    public boolean stacksAmplifier() {
        return false;
    }

    @Override
    public boolean stacksDuration() {
        return true;
    }

    @Override
    public boolean isHarmful() {
        return false;
    }
}
