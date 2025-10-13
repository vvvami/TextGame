package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.world.Direction;

import java.util.List;
import java.util.Random;

public class WanderTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) return false;

        List<Direction> directions = List.of(Direction.values());
        int rand = new Random().nextInt(directions.size());
        source.setDirection(directions.get(rand));
        return (source.receiveAction(source, Action.MOVEMENT));
    }
}
