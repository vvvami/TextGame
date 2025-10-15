package net.vami.game.interactables.custom;

import net.vami.game.Game;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.items.attunement.AttunableItem;
import net.vami.game.interactables.items.attunement.ReinforcedAttunement;
import net.vami.util.TextUtil;

public class AttunementAltar extends Interactable {
    public AttunementAltar(String name) {
        super(name);
        addReceivableAction(Action.ATTACK);
    }

    @Override
    public boolean receiveAttack(Interactable source) {
        if (source instanceof Entity sourceEntity
        && sourceEntity.hasHeldItem()
        && sourceEntity.getHeldItem() instanceof AttunableItem) {
            sourceEntity.getHeldItem().setAttunement(new ReinforcedAttunement());
            Game.display(source, "%s has attuned %s with \"%s\"! %n",
                    sourceEntity.getDisplayName(), sourceEntity.getHeldItem().getDisplayName(),
                    sourceEntity.getHeldItem().getAttunement().getName());
            return true;
        }
        return false;
    }
}
