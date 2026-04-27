package net.vami.game.interactables.interactions.statuses;

import net.vami.game.interactables.entities.Entity;

public class BlessedStatus implements Status {

    @Override
    public void onApply(Entity target, Entity source) {
        if (target != null) {
            if (target.hasSpecifiedStatus(Statuses.WOUNDED)) {
                target.removeStatus(Statuses.WOUNDED);
            }
        }
    }

    @Override
    public void turn(Entity target, Entity source) {
        target.heal(null, (float) target.getStatusInstance(this).getAmplifier() / 2);
    }

    @Override
    public String getName() {
        return "BLSD";
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
