package net.vami.game.interactable.interaction.damagetypes;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class BluntDamage implements DamageType {

    @Override
    public void onHit(Interactable target, Interactable source, float amount) {
        if (!target.hasSpecifiedStatus(Statuses.CRIPPLED)) {
            if (amount * Math.random() > 1) {
                new Status.Instance(
                        Statuses.CRIPPLED, (int) amount / 2, (int) amount, source);
            }
        }
    }

    @Override
    public String getName() {
        return "👊";
    }

    @Override
    public Sound getSound() {
        return Sound.BLUNT_DAMAGE;
    }
}
