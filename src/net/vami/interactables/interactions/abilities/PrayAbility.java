package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.Ability;
import net.vami.interactables.interactions.Status;
import net.vami.interactables.interactions.StatusInstance;

public class PrayAbility extends Ability {
    public static final PrayAbility ABILITY = new PrayAbility();

    protected PrayAbility() {
        super();
    }

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (target instanceof Entity targetEntity
        && source instanceof Entity sourceEntity) {

            targetEntity.heal(sourceEntity, sourceEntity.getLevel() * 1.5f);
            targetEntity.addStatus(new StatusInstance
                    (Status.BLESSED, sourceEntity.getLevel(), sourceEntity.getLevel() * 2, sourceEntity));
        }
        return true;
    }
}
