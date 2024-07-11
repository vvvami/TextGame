package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.statuses.FrenziedStatus;
import net.vami.interactables.interactions.statuses.Status;

public class RageAbility extends Ability {
    public static final RageAbility ABILITY = new RageAbility();

    protected RageAbility() {

        super();
    }

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        if (source instanceof Entity sourceEntity) {
            target.addStatus(new Status.Instance(
                    new FrenziedStatus(), sourceEntity.getLevel(), sourceEntity.getLevel(), sourceEntity));
        }
        return true;
    }
}
