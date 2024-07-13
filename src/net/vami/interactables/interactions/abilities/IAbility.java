package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;

public interface IAbility {


    public boolean useAbility(Interactable source, Interactable target);

    public abstract String getName();

    public default boolean is(IAbility ability) {
        return this.getClass() == ability.getClass();
    }
}