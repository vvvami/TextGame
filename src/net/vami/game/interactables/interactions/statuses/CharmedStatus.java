package net.vami.game.interactables.interactions.statuses;

import net.vami.game.interactables.entities.Entity;

public class CharmedStatus implements Status {
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