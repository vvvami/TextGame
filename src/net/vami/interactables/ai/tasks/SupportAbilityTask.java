package net.vami.interactables.ai.tasks;

import net.vami.game.world.Node;
import net.vami.interactables.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SupportAbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        Entity target = null;
        List<Entity> targetList = new ArrayList<>();
        if (source.isEnemy()) {
            targetList = Node.getEnemies();
        } else {
            targetList = Node.getAllies();
        }
        target = targetList.get(new Random().nextInt(targetList.size()));
        target.receiveAbility(source);
        return true;
    }
}
