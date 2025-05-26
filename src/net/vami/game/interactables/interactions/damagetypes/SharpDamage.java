package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.interactions.statuses.WoundedStatus;

public class SharpDamage implements DamageType {
    public static final SharpDamage get = new SharpDamage();

    @Override
    public void onHit(Interactable target, Interactable source, float amount) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        WoundedStatus.get, (int) amount, (int) amount, source);
            }
    }

    @Override
    public String getName() {
        return "🗡️";
    }

    @Override
    public Sound getSound() {
        return Sound.SHARP_DAMAGE;
    }
}
