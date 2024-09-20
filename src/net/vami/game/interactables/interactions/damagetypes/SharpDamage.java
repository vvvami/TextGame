package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.interactions.statuses.WoundedStatus;

public class SharpDamage implements DamageType {

    @Override
    public void onHit(Entity target, Entity source, float amount) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        new WoundedStatus(), (int) amount, (int) amount, source);
            }
    }

    @Override
    public String getName() {
        return "Sharp";
    }

    @Override
    public Sound getSound() {
        return Sound.SHARP_DAMAGE;
    }
}
