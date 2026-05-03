package net.vami.game.interactable.interaction.abilities;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class RageAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (source instanceof Entity sourceEntity) {
            target.addStatus(new Status.Instance(
                    Statuses.FRENZIED, 1, sourceEntity.getLevel() + 1, sourceEntity));
        }

        return true;
    }

    @Override
    public String getName() {
        return "Rage";
    }

    @Override
    public boolean isSupport() {
        return true;
    }

}
