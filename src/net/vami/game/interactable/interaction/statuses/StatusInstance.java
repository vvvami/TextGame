package net.vami.game.interactable.interaction.statuses;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.interactable.Hoverable;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;

import java.util.UUID;

public class StatusInstance implements Hoverable {
    private int amplifier;
    private int duration;
    private UUID target;
    private UUID source;
    private final Status status;

    public StatusInstance(Status status, int amplifier, int duration, Interactable source) {
        this.status = status;
        this.amplifier = Math.max(1, amplifier);
        this.duration = Math.max(1, duration);
        this.source = source.getID();
    }

    public StatusInstance(Status status, int amplifier, int duration) {
        this.status = status;
        this.amplifier = Math.max(1, amplifier);
        this.duration = Math.max(1, duration);
    }

    public StatusInstance(Status status, int amplifier, Interactable source) {
        this.status = status;
        this.amplifier = Math.max(1, amplifier);
        this.source = source.getID();
    }

    public StatusInstance(Status status, int amplifier) {
        this.status = status;
        this.amplifier = Math.max(1, amplifier);
    }

    public boolean canApply() {
        return this.getStatus().canApply(this.getTarget(), this.getSource());
    }

    public void onApply() {
        this.getStatus().onApply(this.getTarget(), this.getSource());
    }

    public void turn() {
        Interactable interactable = Interactable.getInteractableFromID(target);
        if (interactable.isImmuneTo(this.getStatus())) {
            Display.showText(interactable, "%s's immunity negates %s."
                    , interactable, this.getStatus());
            return;
        }
        this.getStatus().turn(this.getTarget(), this.getSource());

        if (this.getStatus().isPersistent()) return;

        this.duration--;
    }

    public void onEnded() {

        this.getStatus().onEnded(getTarget(), getSource());
    }

    public Status getStatus() {

        return this.status;
    }

    public void setDuration(int duration) {

        this.duration = duration;
    }

    public void setAmplifier(int amplifier) {

        this.amplifier = amplifier;
    }

    public int getDuration() {

        return this.duration;
    }

    public int getAmplifier() {

        return this.amplifier;
    }

    public Entity getSource() {

        return (Entity) Interactable.getInteractableFromID(this.source);
    }

    public Entity getTarget() {

        return (Entity) (Interactable.getInteractableFromID(this.target));
    }

    public void setTarget(Interactable target) {

        this.target = target.getID();
    }

    @Override
    public HoverInfo getHoverInfo() {
        return getStatus().getHoverInfo();
    }

    @Override
    public String getDisplayName() {
        return getStatus().getDisplayName();
    }
}
