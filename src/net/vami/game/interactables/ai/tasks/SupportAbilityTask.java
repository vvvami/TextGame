package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SupportAbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        Entity target = null;
        List<Entity> targetList = new ArrayList<>();
        for (Entity targ : source.getNode().getEntities()) {
            if (source.isFriendlyTo(targ) && source != targ) {
                targetList.add(targ);
            }
        }

        if (targetList.isEmpty()) return false;

        target = targetList.get(new Random().nextInt(targetList.size()));
        return target.receiveAbility(source);
    }
}
