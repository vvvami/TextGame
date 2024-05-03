public class StatusInstance extends Status {
    private int amplifier;
    private int duration;
    private Entity target;
    private Entity source;
    private Status status;

    public StatusInstance(Status status, int amplifier, int duration, Entity source) {
        this.amplifier = amplifier;
        this.duration = duration;
        this.source = source;
        if (target.hasStatus(status)) {
            this.amplifier = status.stacksAmplifier() ? target.getEntityStatus(status).getAmplifier() + this.amplifier : this.amplifier;
            this.duration = status.stacksDuration() ? target.getEntityStatus(status).getDuration() + this.duration : this.duration;
        }
        target.removeStatus(status);
        target.addStatus(this);
    }


    public void onStatusTick() {

    }

    public Status getStatus() {
        return this.status;
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

}
