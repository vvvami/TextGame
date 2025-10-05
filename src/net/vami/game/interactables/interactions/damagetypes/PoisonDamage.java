package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.display.sound.Sound;

public class PoisonDamage implements DamageType {
    public static final PoisonDamage get = new PoisonDamage();

    @Override
    public String getName() {
        return "Poison";
    }

    @Override
    public Sound getSound() {
        return Sound.BLEED_DAMAGE;
    }
}
