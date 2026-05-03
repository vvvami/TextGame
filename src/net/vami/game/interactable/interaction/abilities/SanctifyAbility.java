package net.vami.game.interactable.interaction.abilities;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class SanctifyAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        for (Ability ability : Ability.registry()) {
            if (ability.isSelfCast()) {
                ability.useAbility(source, target);
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Pray";
    }

    @Override
    public boolean isSupport() {
        return true;
    }
}
