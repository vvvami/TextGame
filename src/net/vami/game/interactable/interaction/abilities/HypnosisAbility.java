package net.vami.game.interactable.interaction.abilities;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.StatusInstance;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class HypnosisAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (!(target instanceof Entity targetEntity) || !(source instanceof Entity sourceEntity)) {
            return false;
        }

        if (targetEntity.hasSpecifiedStatus(Statuses.CHARMED)) {
            return false;
        }

        targetEntity.addStatus(new StatusInstance(
                Statuses.CHARMED, 1, sourceEntity.getLevel(), sourceEntity));

        return true;
    }

    @Override
    public String getName() {
        return "Hypnosis";
    }

    @Override
    public boolean isSupport() {
        return false;
    }
}
