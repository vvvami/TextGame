package net.vami.game.interactable.interaction.statuses.custom;

import net.vami.game.interactable.interaction.statuses.Status;

public class CrippledStatus extends Status {

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
