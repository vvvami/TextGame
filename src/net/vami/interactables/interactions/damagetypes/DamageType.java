package net.vami.interactables.interactions.damagetypes;

import net.vami.interactables.entities.Entity;

public abstract interface DamageType {

    default void onHit(Entity target, Entity source, float amount) {

    }

    String getName();

    default boolean is(DamageType damageType) {
        return this.getClass() == damageType.getClass();
    }

}

