package net.vami.game.interactable.interaction.statuses.custom;

import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.IceDamage;
import net.vami.game.interactable.interaction.statuses.Status;

public class FrozenStatus extends Status {

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
