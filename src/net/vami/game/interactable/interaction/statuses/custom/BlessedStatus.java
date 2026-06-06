package net.vami.game.interactable.interaction.statuses.custom;

import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class BlessedStatus extends Status {

    @Override
    public void onApply(Entity target, Entity source) {
        if (target != null) {
            if (target.hasSpecifiedStatus(Statuses.BLEED)) {
                target.removeStatus(Statuses.BLEED);
            }
        }
    }

    @Override
    public void turn(Entity target, Entity source) {
        target.heal(null, (float) target.getStatusInstance(this).getAmplifier() / 2);
    }

    @Override
    public String getName() {
        return "BLSD";
    }

    @Override
    public boolean stacksAmplifier() {
        return false;
    }

    @Override
    public boolean stacksDuration() {
        return true;
    }

    @Override
    public boolean isHarmful() {
        return false;
    }
}
