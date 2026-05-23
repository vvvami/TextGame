package net.vami.game.interactable.interaction.statuses;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.Game;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.interactable.Hoverable;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;

import java.awt.*;
import java.util.UUID;

@JsonAdapter(StatusAdapter.class)
public abstract class Status implements Hoverable {

    public abstract String getName();
    public abstract boolean stacksAmplifier();
    public abstract boolean stacksDuration();
    public abstract boolean isHarmful();


    public boolean isPersistent() {
        return false;
    }

    public boolean canApply(Entity target, Entity source) {
        return true;
    }

    public void turn(Entity target, Entity source) {

    }

    public void onApply(Entity target, Entity source) {

    }

    public void onEnded(Entity target, Entity source) {

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) ||
                this.getClass() == obj.getClass();
    }

    public Color getColor() {
        if (this.isHarmful()) {
            return Color.red;
        }
        return Color.green;
    }

    @Override
    public HoverInfo getHoverInfo() {
        return new HoverInfo(getDisplayName(), "A status effect.");
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
}
