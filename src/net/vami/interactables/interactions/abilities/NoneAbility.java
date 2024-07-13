package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;

public class NoneAbility extends Ability {

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        return false;
    }

    @Override
    public String getName() {
        return "";
    }
}
