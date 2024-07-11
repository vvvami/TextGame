package net.vami.interactables.ai;

import net.vami.game.world.Node;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.Action;

public class TargetTaskType extends TaskType {

    @Override
    public void taskAction(Entity target, Entity source) {
        for (Entity ally : Node.getAllies()) {
            source.setTarget(ally);
        }
    }

}
