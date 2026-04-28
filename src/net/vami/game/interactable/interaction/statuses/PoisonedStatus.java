package net.vami.game.interactable.interaction.statuses;

public class PoisonedStatus implements Status {

    @Override
    public String getName() {
        return "PSN";
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
