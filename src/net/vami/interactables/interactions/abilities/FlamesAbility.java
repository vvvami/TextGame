package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.DamageType;
import net.vami.interactables.interactions.statuses.Status;
import net.vami.interactables.interactions.statuses.BurningStatus;

public class FlamesAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {

        if (source instanceof Entity sourceEntity) {
            target.hurt(sourceEntity, sourceEntity.getLevel(), DamageType.FIRE);
            target.addStatus(new Status.Instance(
                    new BurningStatus(), ((Entity) source).getLevel(), sourceEntity.getLevel() * 2, sourceEntity));
        }
        return true;
    }

    @Override
    public String getName() {
        return "Flames";
    }
}
