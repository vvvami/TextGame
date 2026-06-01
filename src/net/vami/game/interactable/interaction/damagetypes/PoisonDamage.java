package net.vami.game.interactable.interaction.damagetypes;

import net.vami.game.display.sound.Sound;
import net.vami.game.display.sound.Sounds;

public class PoisonDamage extends DamageType {

    @Override
    public String getName() {
        return "Poison";
    }

    @Override
    public Sound getSound() {
        return Sounds.BLEED_DAMAGE;
    }
}
