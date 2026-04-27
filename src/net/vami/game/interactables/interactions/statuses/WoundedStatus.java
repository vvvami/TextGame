package net.vami.game.interactables.interactions.statuses;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.BleedDamage;
import net.vami.game.interactables.interactions.damagetypes.DamageTypes;

public class WoundedStatus implements Status {

    @Override
    public String getName() {
        return "WND";
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
    public void turn(Entity target, Entity source) {
        target.hurt(source, target.getStatusInstance(this).getAmplifier(), DamageTypes.BLEED);
    }
}
