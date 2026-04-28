package net.vami.game.interactable.interaction.statuses;

import net.vami.game.interactable.entity.Entity;
import net.vami.util.TextUtil;

import java.awt.*;

public class CharmedStatus implements Status {

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
    public void onApply(Entity target, Entity source) {
        target.setTarget(null);
    }

    @Override
    public void onEnded(Entity target, Entity source) {
        target.changeRating(source, -0.2f);
    }
}