package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.statuses.IStatus;
import net.vami.interactables.interactions.statuses.BlessedStatus;

public class PrayAbility implements IAbility {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (target instanceof Entity targetEntity
        && source instanceof Entity sourceEntity) {

            targetEntity.heal(sourceEntity, sourceEntity.getLevel() * 1.5f);
            targetEntity.addStatus(new IStatus.Instance
                    (new BlessedStatus(), sourceEntity.getLevel(), sourceEntity.getLevel() * 2, sourceEntity));
        }
        return true;
    }

    @Override
    public String getName() {
        return "Pray";
    }
}
