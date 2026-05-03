package net.vami.game.interactable.item.attunement;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;

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
    public void onHit(ItemInstance item, Interactable source, Entity target, float amount, DamageType damageType) {
        if (source instanceof Entity entitySource) {
            Game.display(source, "%s satiates your thirst. %n", item.getDisplayName());
            entitySource.heal(null, 1);
        }
    }
}
