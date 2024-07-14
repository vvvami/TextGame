package net.vami.interactables.interactions.statuses;

public class FrenziedStatus implements Status {

    @Override
    public String getName() {return "Frenzied";}

    @Override
    public boolean stacksAmplifier() {return true;}

    @Override
    public boolean stacksDuration() {return false;}

    @Override
    public boolean isHarmful() {return false;}
}
