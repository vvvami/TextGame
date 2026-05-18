package net.vami.game.interactable.interaction.statuses;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;

import java.awt.*;
import java.util.UUID;

@JsonAdapter(StatusAdapter.class)
public interface Status {

    String getName();
    boolean stacksAmplifier();
    boolean stacksDuration();
    boolean isHarmful();


    default boolean isPersistent() {
        return false;
    }

    default boolean canApply(Entity target, Entity source) {
        return true;
    }

    default void turn(Entity target, Entity source) {

    }

    default void onApply(Entity target, Entity source) {

    }

    default void onEnded(Entity target, Entity source) {

    }

    // Always use this and not == when comparing statuses
    default boolean is(Status status) {

        return this.getClass() == status.getClass();
    }

    default Color getColor() {
        if (this.isHarmful()) {
            return Color.red;
        }
        return Color.green;
    }

    class Instance {
        private int amplifier;
        private int duration;
        private UUID target;
        private UUID source;
        private final Status status;

        public Instance(Status status, int amplifier, int duration, Interactable source) {
            this.status = status;
            this.amplifier = Math.max(1, amplifier);
            this.duration = Math.max(1, duration);
            this.source = source.getID();
        }

        public Instance(Status status, int amplifier, int duration) {
            this.status = status;
            this.amplifier = Math.max(1, amplifier);
            this.duration = Math.max(1, duration);
        }

        public Instance(Status status, int amplifier, Interactable source) {
            this.status = status;
            this.amplifier = Math.max(1, amplifier);
            this.source = source.getID();
        }

        public Instance(Status status, int amplifier) {
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
                Game.display(interactable, "%s's immunity negates %s."
                ,interactable.getDisplayName(), this.getStatus().getName());
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

    }

}
