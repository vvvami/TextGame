package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.world.Direction;
import net.vami.game.world.Position;

import java.util.List;
import java.util.Random;

public class ChaseTargetTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (!(source.hasTarget()) ||
                (source.getPos().equals(source.getTarget().getPos()))) {
            return false;
        }

        if ((source.getPos().distance(source.getTarget().getPos())) >= 2) {
            source.setTarget(null);
        }

        float initialDist = source.getPos().distance(source.getTarget().getPos());
        Position initialPos = source.getPos();
        List<Direction> directions = List.of(Direction.values());

        while (true) {
            int rand = new Random().nextInt(directions.size());
            Direction randDirect = directions.get(rand);
            initialPos = initialPos.add(randDirect);

            if (initialDist >= initialPos.distance(source.getTarget().getPos())) {
                initialPos = initialPos.subtract(randDirect);
                rand = new Random().nextInt(directions.size());
                randDirect = directions.get(rand);
                initialPos = initialPos.add(randDirect);
            }
            else {
                source.setDirection(randDirect);
                break;
            }
        }
        return source.receiveMovement(source);
    }
}
