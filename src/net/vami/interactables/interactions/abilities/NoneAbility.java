package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;
import net.vami.interactables.interactions.Ability;

public class NoneAbility extends Ability {
    public static final NoneAbility ABILITY = new NoneAbility();

    protected NoneAbility() {
        super();
    }

    @Override
    public boolean useAbility(Interactable source, Interactable target) {
        return false;
    }
}
