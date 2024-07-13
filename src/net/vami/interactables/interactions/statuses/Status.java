package net.vami.interactables.interactions.statuses;

import net.vami.game.TextGame;
import net.vami.interactables.entities.Entity;

public abstract class Status {

    protected Status() {

    }

    public abstract String getName();
    public abstract boolean stacksAmplifier();
    public abstract boolean stacksDuration();
    public abstract boolean isHarmful();

    protected void turn(Entity target, Entity source) {

    }

    protected void onApply(Entity target, Entity source) {

    }

    protected void onEnded(Entity target, Entity source) {

    }

    // Always use this and not == when comparing statuses
    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }

    public static class Instance {
        private int amplifier;
        private int duration;
        private Entity target;
        private Entity source;
        private Status status;

        public Instance(Status status, int amplifier, int duration, Entity source) {
            this.status = status;
            this.amplifier = Math.max(1, duration);
            this.duration = Math.max(1, duration);
            this.source = source;
        }

        public void onApply() {

            this.getStatus().onApply(target, source);
        }

        public void turn() {
            this.getStatus().turn(target, source);
            this.duration--;
        }

        public void onEnded() {

            this.getStatus().onEnded(target, source);
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

            return this.source;
        }

        public Entity getTarget() {

            return this.target;
        }

        public void setTarget(Entity target) {

            this.target = target;
        }

    }

}
