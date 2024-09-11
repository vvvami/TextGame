package net.vami.game.interactables.interactions.statuses;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;

import java.util.UUID;

@JsonAdapter(StatusAdapter.class)
public interface Status {

    String getName();
    boolean stacksAmplifier();
    boolean stacksDuration();
    boolean isHarmful();

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

    class Instance {
        private int amplifier;
        private int duration;
        private UUID target;
        private UUID source;
        private Status status;

        public Instance(Status status, int amplifier, int duration, Entity source) {
            this.status = status;
            this.amplifier = Math.max(1, amplifier);
            this.duration = Math.max(1, duration);
            this.source = source.getID();
        }

        public void onApply() {

            this.getStatus().onApply(getTarget(), getSource());
        }

        public void turn() {
            this.getStatus().turn(getTarget(), getSource());
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

        public void setTarget(Entity target) {

            this.target = target.getID();
        }

    }

}
