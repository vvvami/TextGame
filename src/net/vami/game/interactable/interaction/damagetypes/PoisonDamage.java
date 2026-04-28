package net.vami.game.interactable.interaction.damagetypes;

import net.vami.game.display.sound.Sound;

public class PoisonDamage implements DamageType {

    @Override
    public String getName() {
        return "Poison";
    }

    @Override
    public Sound getSound() {
        return Sound.BLEED_DAMAGE;
    }
}
