package net.vami.game.interactable.interaction.statuses;

import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.FireDamage;

public class BurningStatus implements Status {

    @Override
    public String getName() {
        return "BRN";
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
        target.hurt(source, target.getStatusInstance(this).getAmplifier(), new FireDamage());
    }
}
