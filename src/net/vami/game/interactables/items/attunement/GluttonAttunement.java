package net.vami.game.interactables.items.attunement;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.items.Item;
import net.vami.util.TextUtil;

public class GluttonAttunement implements Attunement {
    @Override
    public String getName() {
        return "Glutton";
    }

    @Override
    public boolean isCurse() {
        return false;
    }

    @Override
    public void onHit(Item item, Interactable source, Entity target, float amount, DamageType damageType) {
        if (source instanceof Entity entitySource) {
            TextUtil.display("%s satiates your thirst. %n", item.getDisplayName());
            entitySource.heal(null, 1);
        }
    }
}
