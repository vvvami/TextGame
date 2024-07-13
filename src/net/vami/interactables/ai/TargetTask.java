package net.vami.interactables.ai;

import net.vami.game.world.Node;
import net.vami.interactables.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TargetTask extends Task {

    @Override
    public void taskAction(Entity source) {
        List<Entity> targetList = new ArrayList<>();
        if (source.isEnemy()) {
            targetList = Node.getAllies();
        } else {
            targetList = Node.getEnemies();
        }
        source.setTarget(targetList.get(new Random().nextInt(targetList.size())));

        System.out.println(source.getTarget().getName());

        if (source.hasTarget() && source.getBrain().hasTask(new AttackTask())) {
            new AttackTask().taskAction(source);
        }
    }
}
