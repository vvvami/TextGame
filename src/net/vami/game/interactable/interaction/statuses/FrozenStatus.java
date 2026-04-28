package net.vami.game.interactable.interaction.statuses;

import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.IceDamage;

public class FrozenStatus implements Status {

    @Override
    public String getName() {
        return "FRZN";
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
        target.hurt(source, (float) target.getStatusInstance(this).getAmplifier() / 2, new IceDamage());
    }
}
