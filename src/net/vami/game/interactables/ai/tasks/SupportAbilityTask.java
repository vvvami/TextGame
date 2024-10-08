package net.vami.game.interactables.ai.tasks;

import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SupportAbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        Entity target = null;
        List<Entity> targetList = new ArrayList<>();
        if (source.isEnemy()) {
            targetList = source.getNode().getEnemies();
        } else {
            targetList = source.getNode().getAllies();
        }
        target = targetList.get(new Random().nextInt(targetList.size()));
        target.receiveAbility(source);
        return true;
    }
}
