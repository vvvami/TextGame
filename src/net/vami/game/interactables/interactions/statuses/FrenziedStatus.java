package net.vami.game.interactables.interactions.statuses;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.Modifier;
import net.vami.game.interactables.interactions.ModifierType;

public class FrenziedStatus implements Status {

    @Override
    public void onApply(Entity target, Entity source) {
        Modifier frenzyModifier = new Modifier("frenzy", ModifierType.DAMAGE, 10f);
        target.addModifier(frenzyModifier);
        Status.super.onApply(target, source);
    }

    @Override
    public void onEnded(Entity target, Entity source) {
        target.removeModifier("frenzy");

        Status.super.onEnded(target, source);
    }

    @Override
    public String getName() {return "Frenzied";}

    @Override
    public boolean stacksAmplifier() {return true;}

    @Override
    public boolean stacksDuration() {return false;}

    @Override
    public boolean isHarmful() {return false;}
}
