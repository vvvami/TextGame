package net.vami.game.interactables.interactions.statuses;

import net.vami.game.interactables.ai.EntityMood;
import net.vami.game.interactables.entities.Entity;

public class CharmedStatus implements Status {
    public static final CharmedStatus get = new CharmedStatus();
    private EntityMood prevMood;

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
        target.setMood(source, EntityMood.FRIENDLY);
    }

    @Override
    public void onApply(Entity target, Entity source) {
        prevMood = target.getMood(source);
        target.setMood(source, EntityMood.FRIENDLY);
    }

    @Override
    public void onEnded(Entity target, Entity source) {
        target.setMood(source, prevMood);
        target.changeRating(source, -0.2f);
    }
}
