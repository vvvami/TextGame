package net.vami.game.interactable.interaction.statuses.custom;

import net.vami.game.interactable.interaction.statuses.Status;

public class PoisonedStatus extends Status {

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
