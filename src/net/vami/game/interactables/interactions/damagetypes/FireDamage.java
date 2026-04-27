package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.statuses.BurningStatus;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.interactions.statuses.Statuses;

public class FireDamage implements DamageType {

    @Override
    public void onHit(Interactable target, Interactable source, float amount) {
        if (!target.hasSpecifiedStatus(Statuses.BURNING)) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        Statuses.BURNING, (int) amount / 2, (int) amount, source);
            }
        }
    }

    @Override
    public String getName() {
        return "🔥";
    }

    @Override
    public Sound getSound() {
        return Sound.FIRE_DAMAGE;
    }
}
