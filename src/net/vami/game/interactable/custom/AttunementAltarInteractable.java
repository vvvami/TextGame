package net.vami.game.interactable.custom;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.item.attunement.AttunableItem;
import net.vami.game.interactable.item.attunement.Attunement;
import net.vami.game.interactable.item.attunement.Attunements;
import net.vami.game.interactable.item.attunement.ReinforcedAttunement;
import net.vami.game.interactable.loot.Pools;

public class AttunementAltarInteractable extends Interactable {
    public AttunementAltarInteractable(String name) {
        super(name);
        addReceivableAction(Action.ATTACK);
    }

    @Override
    public boolean receiveAttack(Interactable source) {
        if (source instanceof Entity sourceEntity
        && sourceEntity.hasHeldItem()
        && sourceEntity.getHeldItem().get() instanceof AttunableItem) {
            sourceEntity.getHeldItem().setAttunement((Attunement) Pools.ALTAR_ATTUNEMENTS.choose());
            Game.display(source, "%s has attuned %s with \"%s\"! %n",
                    sourceEntity.getDisplayName(), sourceEntity.getHeldItem().getDisplayName(),
                    sourceEntity.getHeldItem().getAttunement().getName());
            return true;
        }
        return false;
    }
}
