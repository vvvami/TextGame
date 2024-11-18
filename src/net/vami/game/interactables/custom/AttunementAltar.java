package net.vami.game.interactables.custom;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.items.attunement.AttunableItem;
import net.vami.game.interactables.items.attunement.ReinforcedAttunement;
import net.vami.util.TextUtil;
import org.w3c.dom.Text;

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
            TextUtil.display(source, "%s has attuned %s with %s! %n",
                    sourceEntity.getDisplayName(), sourceEntity.getHeldItem().getDisplayName(),
                    sourceEntity.getHeldItem().getAttunement().name());
            return true;
        }
        return false;
    }
}
