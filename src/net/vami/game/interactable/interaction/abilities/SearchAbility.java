package net.vami.game.interactable.interaction.abilities;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.world.Position;

import java.util.*;
import java.util.stream.Collectors;

public class SearchAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        List<Interactable> list = Interactable.getInteractableMap().values().stream().toList();
        for (Interactable interactable : Interactable.getInteractableMap().values()) {

            if (isTargetValid(source, interactable)) {
                int distance = source.getPos().distance(interactable.getPos());

                if (distance <= 3) {
                    Game.display(source, "You are alerted to the presence of [%s] at %s%n",
                            interactable.getName(), interactable.getPos().toString());
                    ((Entity) source).setTarget((Entity) target);
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
