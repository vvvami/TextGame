package net.vami.game.interactables.interactions.abilities;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Wolf;

public class SummoningAbility implements Ability {
    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        Entity entityTarget = (Entity) target;
        Entity entitySource = (Entity) source;
        Wolf summon = new Wolf(source.getName() + "'s Summon", new Entity.Attributes()
                .level(entitySource.getLevel()));

        if (entityTarget.isEnemy()) {
            summon.setEnemy(false);
        } else {
            summon.setEnemy(true);
        }
        summon.setTarget(entityTarget);
        Entity.spawn(summon, entitySource.getPos());

        return true;
    }

    @Override
    public String getName() {
        return "Summoning";
    }

    @Override
    public boolean isSupport() {
        return false;
    }
}
