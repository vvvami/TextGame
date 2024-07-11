package net.vami.interactables.interactions.statuses;

public class CrippledStatus extends Status {

    @Override
    public String getName() {
        return "Crippled";
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
}
