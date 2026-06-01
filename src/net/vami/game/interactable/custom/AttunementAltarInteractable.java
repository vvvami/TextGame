package net.vami.game.interactable.custom;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.item.attunement.ItemAttunable;
import net.vami.game.interactable.item.attunement.Attunement;
import net.vami.game.interactable.loot.Pools;

public class AttunementAltarInteractable extends Interactable {
    public AttunementAltarInteractable(String name) {
        super(name);
        addReceivableAction(Action.ATTACK);
    }

    public AttunementAltarInteractable() {
        this("Attuner");
    }

    @Override
    public boolean receiveAttack(Interactable source) {
        if (source instanceof Entity sourceEntity
        && sourceEntity.hasHeldItem()
        && sourceEntity.getHeldItem().get() instanceof ItemAttunable) {
            sourceEntity.getHeldItem().setAttunement((Attunement) Pools.ALTAR_ATTUNEMENTS.choose());
            Display.showText(source, "%s has attuned %s with \"%s\"! %n",
                    sourceEntity, sourceEntity.getHeldItem(),
                    sourceEntity.getHeldItem().getAttunement());
            return true;
        }
        return false;
    }
}
