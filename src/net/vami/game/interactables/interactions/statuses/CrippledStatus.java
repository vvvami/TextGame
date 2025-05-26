package net.vami.game.interactables.interactions.statuses;

public class CrippledStatus implements Status {
    public static final CrippledStatus get = new CrippledStatus();

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
