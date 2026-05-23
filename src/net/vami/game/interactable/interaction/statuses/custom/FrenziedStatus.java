package net.vami.game.interactable.interaction.statuses.custom;

import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;
import net.vami.game.interactable.interaction.statuses.Status;

public class FrenziedStatus extends Status {

    @Override
    public void onApply(Entity target, Entity source) {
        if (target.hasModifier("frenzy")) {

            target.removeModifier("frenzy");
        }

        target.addModifier(new Modifier(
                "frenzy",
                ModifierType.DAMAGE,
                target.getStatusInstance(this).getAmplifier()));

        super.onApply(target, source);
    }

    @Override
    public void onEnded(Entity target, Entity source) {
        target.removeModifier("frenzy");

        super.onEnded(target, source);
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
