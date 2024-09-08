package net.vami.game.interactables.interactions.patrons;

import net.vami.game.interactables.interactions.abilities.Ability;
import net.vami.game.interactables.interactions.damagetypes.DamageType;

public interface Patron {
    String name();

    int level();
    int maxHealth();
    int armor();
    int baseDamage();
    DamageType damageType();
    Ability ability();
}
