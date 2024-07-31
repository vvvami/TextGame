package net.vami.game.interactables.interactions.damagetypes;

import net.vami.game.interactables.entities.Entity;

public abstract interface DamageType {

    default void onHit(Entity target, Entity source, float amount) {

    }

    String getName();

    default boolean is(DamageType damageType) {
        return this.getClass() == damageType.getClass();
    }

}

