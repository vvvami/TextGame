package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.display.sound.Sound;

public class BleedDamage implements DamageType {
    public static final BleedDamage get = new BleedDamage();

    @Override
    public String getName() {
        return "🩸";
    }

    @Override
    public Sound getSound() {
        return Sound.BLEED_DAMAGE;
    }
}
