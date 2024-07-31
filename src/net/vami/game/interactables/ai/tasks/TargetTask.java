package net.vami.game.interactables.ai.tasks;

import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TargetTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        List<Entity> targetList = new ArrayList<>();
        if (source.isEnemy()) {
            targetList = Node.getAllies();
        } else {
            targetList = Node.getEnemies();
        }
        if (targetList.size() <= 0) {
            return false;
        }
        source.setTarget(targetList.get(new Random().nextInt(targetList.size())));
        return true;
    }
}
