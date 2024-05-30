package net.vami.game;

import net.vami.interactables.entities.Entity;

public class StatusInstance {
    private int amplifier;
    private int duration;
    private Entity target;
    private Entity source;
    private Status status;

    public StatusInstance(Status status, int amplifier, int duration, Entity source) {
        this.status = status;
        this.amplifier = amplifier;
        this.duration = duration;
        this.source = source;
    }


    public void turn() {
        switch (status) {
            case BURNING -> target.hurt(source, amplifier, DamageType.FIRE);
            case FROZEN -> target.hurt(source, amplifier, DamageType.ICE);
            case BLEEDING -> target.hurt(source, amplifier, DamageType.BLEED);
            case BLESSED -> target.heal(source, amplifier);
        }
        this.duration--;
    }

    private void statusCountersCancel() {
        if (target.hasSpecifiedStatus(status.getCounterStatus())) {
            target.removeStatus(status.getCounterStatus());
            target.removeStatus(status);
        }
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
