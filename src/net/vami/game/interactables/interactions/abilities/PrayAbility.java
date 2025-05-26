package net.vami.game.interactables.interactions.abilities;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.interactions.statuses.BlessedStatus;

public class PrayAbility implements Ability {
    public static final PrayAbility get = new PrayAbility();

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (target instanceof Entity targetEntity
        && source instanceof Entity sourceEntity) {

            targetEntity.heal(sourceEntity, sourceEntity.getLevel() * 1.5f);
            targetEntity.addStatus(new Status.Instance
                    (BlessedStatus.get, sourceEntity.getLevel(), sourceEntity.getLevel() * 2, sourceEntity));
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
