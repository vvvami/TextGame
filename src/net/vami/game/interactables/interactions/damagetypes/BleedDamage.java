package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.display.sound.Sound;

public class BleedDamage implements DamageType {
    @Override
    public String getName() {
        return "Bleed";
    }

    @Override
    public Sound getSound() {
        return Sound.BLEED_DAMAGE;
    }
}
