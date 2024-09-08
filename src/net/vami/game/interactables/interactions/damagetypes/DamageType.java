package net.vami.game.interactables.interactions.damagetypes;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.world.DamageTypeAdapter;

@JsonAdapter(DamageTypeAdapter.class)
public interface DamageType {

    default void onHit(Entity target, Entity source, float amount) {

    }

    String getName();

    default boolean is(DamageType damageType) {
        return this.getClass() == damageType.getClass();
    }

}

