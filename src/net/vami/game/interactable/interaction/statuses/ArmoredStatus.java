package net.vami.game.interactable.interaction.statuses;

import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;

public class ArmoredStatus implements Status {

    @Override
    public void onApply(Entity target, Entity source) {
        if (target != null) {
            if (target.hasModifier("Armored")) {
                target.removeModifier("Armored");
            }

            target.addModifier(new Modifier("Armored",
                    ModifierType.ARMOR,
                    target.getStatusInstance(this).getAmplifier()));
        }
    }

    @Override
    public void onEnded(Entity target, Entity source) {
        if (target != null) {
            target.removeModifier("Armored");
        }
    }

    @Override
    public String getName() {
        return "BLSD";
    }

    @Override
    public boolean stacksAmplifier() {
        return false;
    }

    @Override
    public boolean stacksDuration() {
        return true;
    }

    @Override
    public boolean isHarmful() {
        return false;
    }
}
