package net.vami.game.interactable.interaction.abilities;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.StatusInstance;
import net.vami.game.interactable.interaction.statuses.Statuses;

public class FlamesAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {

        if (source instanceof Entity sourceEntity) {
            target.hurt(sourceEntity, sourceEntity.getLevel(), DamageTypes.FIRE);
            target.addStatus(new StatusInstance(
                    Statuses.BURNING, sourceEntity.getLevel(), sourceEntity.getLevel() * 2, sourceEntity));
        }
        return true;
    }

    @Override
    public String getName() {
        return "Flames";
    }

    @Override
    public boolean isSupport() {
        return false;
    }
}
