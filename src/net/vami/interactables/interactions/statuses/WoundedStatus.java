package net.vami.interactables.interactions.statuses;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.DamageType;

public class WoundedStatus extends Status {

    @Override
    public String getName() {
        return "Wounded";
    }

    @Override
    public boolean stacksAmplifier() {
        return true;
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
        target.hurt(source, target.getStatusInstance(this).getAmplifier(), DamageType.BLEED);
    }
}
