package net.vami.game.interactable.interaction.damagetypes;

import net.vami.game.display.sound.Sound;

public class BleedDamage extends DamageType {

    @Override
    public String getName() {
        return "BLD";
    }

    @Override
    public Sound getSound() {
        return Sound.BLEED_DAMAGE;
    }
}
