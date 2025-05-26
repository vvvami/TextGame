package net.vami.game.interactables.interactions.statuses;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.IceDamage;

public class FrozenStatus implements Status {
    public static final FrozenStatus get = new FrozenStatus();

    @Override
    public String getName() {
        return "Frozen";
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
