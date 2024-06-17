package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.Ability;
import net.vami.interactables.interactions.DamageType;
import net.vami.interactables.interactions.Status;
import net.vami.interactables.interactions.StatusInstance;

public class FlamesAbility extends Ability {
    public static final FlamesAbility ABILITY = new FlamesAbility();

    protected FlamesAbility() {
        super();
    }

    @Override
    public boolean useAbility(Interactable source, Interactable target) {

        if (source instanceof Entity sourceEntity) {
            target.hurt(sourceEntity, sourceEntity.getLevel(), DamageType.FIRE);
            target.addStatus(new StatusInstance(
                    Status.BURNING, ((Entity) source).getLevel(), sourceEntity.getLevel() * 2, sourceEntity));
        }
        return true;
    }
}
