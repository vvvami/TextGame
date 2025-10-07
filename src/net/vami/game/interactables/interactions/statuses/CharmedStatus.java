package net.vami.game.interactables.interactions.statuses;

import net.vami.game.interactables.ai.EntityMood;
import net.vami.game.interactables.ai.tasks.SupportAbilityTask;
import net.vami.game.interactables.ai.tasks.Task;
import net.vami.game.interactables.entities.Entity;
import net.vami.util.TextUtil;

import java.awt.*;

public class CharmedStatus implements Status {
    public static final CharmedStatus get = new CharmedStatus();
    private EntityMood prevMood;
    private Task prevTask;

    @Override
    public String getName() {
        return TextUtil.setColor("CHM", Color.pink) ;
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
    public boolean canApply(Entity target, Entity source) {
        return target != source;
    }

    @Override
    public void turn(Entity target, Entity source) {
        target.setMood(source, EntityMood.FRIENDLY);
        target.addTask(new SupportAbilityTask(), 100);
    }

    @Override
    public void onApply(Entity target, Entity source) {
        prevMood = target.getMood(source);
        target.setMood(source, EntityMood.FRIENDLY);
        target.setTarget(null);
        prevTask = target.getTask(new SupportAbilityTask());
    }

    @Override
    public void onEnded(Entity target, Entity source) {
        target.setMood(source, prevMood);
        if (prevTask != null) {
            target.addTask(prevTask, prevTask.getPriority());
        }
        target.changeRating(source, -0.2f);
    }
}