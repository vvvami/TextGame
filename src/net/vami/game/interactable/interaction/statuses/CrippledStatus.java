package net.vami.game.interactable.interaction.statuses;

public class CrippledStatus implements Status {

    @Override
    public String getName() {
        return "CRPL";
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
