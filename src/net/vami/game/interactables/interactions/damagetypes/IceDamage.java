package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.statuses.FrozenStatus;
import net.vami.game.interactables.interactions.statuses.Status;

public class IceDamage implements DamageType {

    @Override
    public void onHit(Entity target, Entity source, float amount) {
        if (!target.hasSpecifiedStatus(new FrozenStatus())) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        new FrozenStatus(), (int) amount, (int) amount, source);
            }
        }
    }

    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public Sound getSound() {
        return Sound.ICE_DAMAGE;
    }
}
