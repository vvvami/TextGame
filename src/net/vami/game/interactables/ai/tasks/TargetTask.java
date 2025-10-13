package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.statuses.CharmedStatus;
import net.vami.util.LogUtil;
import net.vami.util.LoggerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TargetTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (source.hasTarget()) {
            return false;
        }

        List<Entity> targetList = new ArrayList<>();

        for (Entity target : source.getNode().getEntities()) {
            if (target == source) {continue;}

            boolean shouldTarget = source.isHostileTo(target);
            if (source.hasSpecifiedStatus(CharmedStatus.get)) {
                shouldTarget = !shouldTarget;
            }

            if (shouldTarget) {
                targetList.add(target);
            }
        }
        if (targetList.isEmpty()) {
            return false;
        }
        source.setTarget(targetList.get(new Random().nextInt(targetList.size())));
        LogUtil.Log(LoggerType.DEBUG,
                "Selected target: %s", source.getTarget().getName());
        return true;
    }
}
