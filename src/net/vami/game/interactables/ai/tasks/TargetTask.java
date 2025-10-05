package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TargetTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        List<Entity> targetList = new ArrayList<>();

        for (Entity target : source.getNode().getEntities()) {
            if (source.isHostileTo(target)) {
                targetList.add(target);
            }
        }
        if (targetList.isEmpty()) {
            return false;
        }
        source.setTarget(targetList.get(new Random().nextInt(targetList.size())));
        return true;
    }
}
