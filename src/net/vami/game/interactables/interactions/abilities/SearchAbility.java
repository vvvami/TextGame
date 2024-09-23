package net.vami.game.interactables.interactions.abilities;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.util.TextUtil;

public class SearchAbility implements Ability {
    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        for (Interactable interactable : Interactable.getInteractableMap().values()) {
            if (interactable.getPos() != null &&
                interactable instanceof Entity entity &&
                    entity != source) {
                float distance = source.getPos().distance(entity.getPos());
                if (distance <= 2) {
                    TextUtil.display(source, "%s is %s acres away %s. %n",
                            entity.getName(), Math.round(distance), entity.getPos().toString());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "Search";
    }

    @Override
    public boolean isSupport() {
        return true;
    }
}
