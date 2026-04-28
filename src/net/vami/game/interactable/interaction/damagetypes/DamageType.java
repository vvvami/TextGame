package net.vami.game.interactable.interaction.damagetypes;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.world.DamageTypeAdapter;

@JsonAdapter(DamageTypeAdapter.class)
public interface DamageType {

    default void onHit(Interactable target, Interactable source, float amount) {

    }

    String getName();

    Sound getSound();

    default boolean is(DamageType damageType) {
        return this.getClass() == damageType.getClass();
    }

}

