package net.vami.game.interactable.ai.tasks;

import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.statuses.Statuses;
import net.vami.util.LogUtil;
import net.vami.util.LoggerType;

import java.util.ArrayList;
import java.util.List;

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
            if (source.hasSpecifiedStatus(Statuses.CHARMED)) {
                shouldTarget = !shouldTarget;
            }

            if (shouldTarget) {
                targetList.add(target);
            }
        }
        if (targetList.isEmpty()) {
            return false;
        }
        source.setTarget(targetList.getFirst());
        LogUtil.Log(LoggerType.DEBUG,
                "Selected target: %s", source.getTarget().getName());
        return true;
    }
}
