package net.vami.game.interactables.interactions.abilities;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.statuses.FrenziedStatus;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.interactions.statuses.Statuses;

public class RageAbility implements Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (source instanceof Entity sourceEntity) {
            target.addStatus(new Status.Instance(
                    Statuses.FRENZIED, sourceEntity.getLevel(), sourceEntity.getLevel() + 1, sourceEntity));
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
