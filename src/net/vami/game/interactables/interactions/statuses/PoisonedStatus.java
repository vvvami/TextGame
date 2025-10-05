package net.vami.game.interactables.interactions.statuses;

public class PoisonedStatus implements Status {
    public static final PoisonedStatus get = new PoisonedStatus();

    @Override
    public String getName() {
        return "Poisoned";
    }

    @Override
    public boolean stacksAmplifier() {
        return true;
    }

    @Override
    public boolean stacksDuration() {
        return true;
    }

    @Override
    public boolean isHarmful() {
        return true;
    }
}
