package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.world.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MoveTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        List<Direction> directions = List.of(Direction.values());
        int rand = new Random().nextInt(directions.size());
        source.setDirection(directions.get(rand));
        return (source.receiveAction(source, Action.MOVEMENT));
    }
}
