package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.statuses.Status;
import net.vami.interactables.interactions.statuses.BlessedStatus;

public class PrayAbility implements Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (target instanceof Entity targetEntity
        && source instanceof Entity sourceEntity) {

            targetEntity.heal(sourceEntity, sourceEntity.getLevel() * 1.5f);
            targetEntity.addStatus(new Status.Instance
                    (new BlessedStatus(), sourceEntity.getLevel(), sourceEntity.getLevel() * 2, sourceEntity));
        }
        return true;
    }

    @Override
    public String getName() {
        return "Pray";
    }
}
