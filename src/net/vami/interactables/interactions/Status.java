package net.vami.interactables.interactions;

import net.vami.game.Main;
import net.vami.interactables.entities.Entity;

public class Status {


    private String name;
    private boolean harmful;
    private boolean stacksDuration;
    private boolean stacksAmplifier;


    protected Status(String name, boolean stacksAmplifier, boolean stacksDuration, boolean harmful) {
        this.stacksAmplifier = stacksAmplifier;
        this.stacksDuration = stacksDuration;
        this.harmful = harmful;

        if (harmful) {
            this.name = Main.ANSI_RED + name + Main.ANSI_RESET;
        }
        else {
            this.name = Main.ANSI_GREEN + name + Main.ANSI_RESET;
        }
    }

    public String getName() {
        return name;
    }


    public boolean isHarmful() {
        return harmful;
    }

    public boolean stacksDuration() {
        return stacksDuration;
    }

    public boolean stacksAmplifier() {
        return stacksAmplifier;
    }

    protected void turn(Entity target, Entity source) {

    }

    protected void onApply(Entity target, Entity source) {

    }

    protected void onEnded(Entity target, Entity source) {

    }

    public static class Instance {
        private int amplifier;
        private int duration;
        private Entity target;
        private Entity source;
        private Status status;

        public Instance(Status status, int amplifier, int duration, Entity source) {
            this.status = status;
            this.amplifier = amplifier;
            this.duration = duration;
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
            this.getStatus().onApply(target, source);
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
