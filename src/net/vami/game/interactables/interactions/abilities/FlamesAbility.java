package net.vami.game.interactables.interactions.abilities;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.interactions.statuses.Status;
import net.vami.game.interactables.interactions.statuses.BurningStatus;

public class FlamesAbility implements Ability {

    public static final FlamesAbility get = new FlamesAbility();

    @Override
    public boolean useAbility(Interactable source, Interactable target) {

        if (source instanceof Entity sourceEntity) {
            target.hurt(sourceEntity, sourceEntity.getLevel(), FireDamage.get);
            target.addStatus(new Status.Instance(
                    BurningStatus.get, sourceEntity.getLevel(), sourceEntity.getLevel() * 2, sourceEntity));
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
