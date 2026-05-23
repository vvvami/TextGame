package net.vami.game.interactable.interaction.damagetypes;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.StatusInstance;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class SharpDamage extends DamageType {

    @Override
    public void onHit(Interactable target, Interactable source, float amount) {
            if (amount * Math.random() > 1) {
                new StatusInstance(
                        Statuses.WOUNDED, (int) amount, (int) amount, source);
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
