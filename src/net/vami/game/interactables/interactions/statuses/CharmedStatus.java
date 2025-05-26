package net.vami.game.interactables.interactions.statuses;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.action.Action;

import java.util.HashSet;
import java.util.Set;

public class CharmedStatus implements Status {
    public static final CharmedStatus get = new CharmedStatus();

    @Override
    public String getName() {
        return "Charmed";
    }

    @Override
    public boolean stacksAmplifier() {
        return false;
    }

    @Override
    public boolean stacksDuration() {
        return false;
    }

    @Override
    public boolean isHarmful() {
        return true;
    }

    @Override
    public void turn(Entity target, Entity source) {
        if (target.getTarget() != null) {
            if (target.getTarget().isEnemy() == source.isEnemy()) {
                target.setTarget(null);
            }
        }
    }

    Set<Action> actions = new HashSet<>();

    @Override
    public void onApply(Entity target, Entity source) {

        target.setEnemy(source.isEnemy());
        target.setTarget(null);
    }

    @Override
    public void onEnded(Entity target, Entity source) {

        target.setEnemy(!source.isEnemy());
    }
}
