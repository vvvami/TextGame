package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.statuses.BurningStatus;
import net.vami.game.interactables.interactions.statuses.Status;

public class FireDamage implements DamageType {

    @Override
    public void onHit(Entity target, Entity source, float amount) {
        if (!target.hasSpecifiedStatus(new BurningStatus())) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        new BurningStatus(), (int) amount / 2, (int) amount, source);
            }
        }
    }

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public Sound getSound() {
        return Sound.FIRE_DAMAGE;
    }
}
