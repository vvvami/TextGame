package net.vami.game.interactable.interaction.abilities;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.ai.EntityMood;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.entity.WolfEntity;

public class SummonAbility implements Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        Entity entitySource = (Entity) source;
        WolfEntity summon = new WolfEntity(
                source.getName() + "'s Wolf",
                new Entity.Attributes()
                .level(entitySource.getLevel()));

        summon.setMood(source, EntityMood.FRIENDLY);
        Interactable.spawnInteractable(summon, entitySource.getPos());

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
