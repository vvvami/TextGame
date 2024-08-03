package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.world.Direction;
import net.vami.game.world.Position;

import java.util.List;
import java.util.Random;

public class ChaseTargetTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (!(source.hasTarget()) || source.getPos().equals(source.getTarget().getPos())) {
            return false;
        }

        float initialDist = source.getPos().distance(source.getTarget().getPos());
        Position initialPos = source.getPos();
        List<Direction> directions = List.of(Direction.values());

        while (true) {
            int rand = new Random().nextInt(directions.size());
            Direction randDirect = directions.get(rand);
            initialPos = initialPos.add(randDirect.pos);

            if (initialDist >= initialPos.distance(source.getTarget().getPos())) {
                break;
            }
            else {
               initialPos = initialPos.subtract(randDirect.pos);
            }
        }
        return source.receiveMovement(source);
    }
}
