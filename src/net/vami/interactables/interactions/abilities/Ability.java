package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;

public interface Ability {


    public boolean useAbility(Interactable source, Interactable target);

    public abstract String getName();

    public default boolean is(Ability ability) {
        return this.getClass() == ability.getClass();
    }
}