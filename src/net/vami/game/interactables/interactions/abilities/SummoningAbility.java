package net.vami.game.interactables.interactions.abilities;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.ai.EntityMood;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.WolfEntity;

public class SummoningAbility implements Ability {
    public static final SummoningAbility get = new SummoningAbility();

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        Entity entitySource = (Entity) source;
        WolfEntity summon = new WolfEntity(
                source.getName() + "'s Wolf",
                new Entity.Attributes()
                .level(entitySource.getLevel()));

        summon.setMood(source, EntityMood.FRIENDLY);
        Entity.spawnInteractable(summon, entitySource.getPos());

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
