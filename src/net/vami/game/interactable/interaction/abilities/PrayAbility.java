package net.vami.game.interactable.interaction.abilities;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.StatusInstance;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class PrayAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (target instanceof Entity targetEntity
        && source instanceof Entity sourceEntity) {

            targetEntity.heal(sourceEntity, sourceEntity.getLevel());

            targetEntity.addStatus(new StatusInstance
                    (Statuses.BLESSED,
                            Math.max(1 ,sourceEntity.getLevel() / 2),
                            Math.max(1 ,sourceEntity.getLevel() / 2),
                            sourceEntity));
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
