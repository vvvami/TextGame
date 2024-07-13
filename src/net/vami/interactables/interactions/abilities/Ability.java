package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;

import java.util.HashMap;

public abstract class Ability {


    public boolean useAbility(Interactable source, Interactable target) {

        return false;
    }


    public abstract String getName();


    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }
}