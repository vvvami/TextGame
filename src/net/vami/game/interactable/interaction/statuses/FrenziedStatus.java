package net.vami.game.interactable.interaction.statuses;

import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;

public class FrenziedStatus implements Status {

    @Override
    public void onApply(Entity target, Entity source) {
        if (target.hasModifier("frenzy")) {

            target.removeModifier("frenzy");
        }

        target.addModifier(new Modifier(
                "frenzy",
                ModifierType.DAMAGE,
                target.getStatusInstance(this).getAmplifier()));

        Status.super.onApply(target, source);
    }

    @Override
    public void onEnded(Entity target, Entity source) {
        target.removeModifier("frenzy");

        Status.super.onEnded(target, source);
    }

    @Override
    public String getName() {return "FRNZ";}

    @Override
    public boolean stacksAmplifier() {return true;}

    @Override
    public boolean stacksDuration() {return false;}

    @Override
    public boolean isHarmful() {return false;}
}
