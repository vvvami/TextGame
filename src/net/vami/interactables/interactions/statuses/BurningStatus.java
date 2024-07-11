package net.vami.interactables.interactions.statuses;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.DamageType;

public class BurningStatus extends Status {

    @Override
    public String getName() {
        return "Burning";
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
        return true;
    }

    @Override
    protected void turn(Entity target, Entity source) {
        target.hurt(source, target.getStatusInstance(this).getAmplifier(), DamageType.FIRE);
    }
}
