package net.vami.interactables.interactions.statuses;

public class CrippledStatus implements IStatus {

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
