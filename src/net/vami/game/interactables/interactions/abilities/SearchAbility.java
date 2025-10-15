package net.vami.game.interactables.interactions.abilities;

import net.vami.game.Game;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.util.TextUtil;

public class SearchAbility implements Ability {
    public static final SearchAbility get = new SearchAbility();

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        for (Interactable interactable : Interactable.getInteractableMap().values()) {

            if (isTargetValid(source, interactable)) {
                float distance = source.getPos().distance(interactable.getPos());

                if (distance <= 3) {
                    Game.display(source, "%s is %s acres away %s. %n",
                            interactable.getName(), Math.round(distance), interactable.getPos().toString());
                    return true;
                }
            }
        }
        Game.display(source, "Your search finds nothing. %n");
        return false;
    }

    private boolean isTargetValid(Interactable source, Interactable target) {
        return target.getPos() != null &&
                target instanceof Entity entity &&
                entity != source;
    }

    @Override
    public String getName() {
        return "Search";
    }

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public boolean isSelfCast() {
        return true;
    }
}
