package net.vami.game.interactable.interaction.patrons;

import net.vami.game.interactable.entity.PlayerEntity;
import net.vami.game.interactable.interaction.abilities.Ability;
import net.vami.game.interactable.interaction.damagetypes.DamageType;

public interface Patron {
    String name();

    int level();
    int maxHealth();
    int armor();
    int baseDamage();
    DamageType damageType();
    Ability ability();

    default void init(PlayerEntity player) {

    }

}
