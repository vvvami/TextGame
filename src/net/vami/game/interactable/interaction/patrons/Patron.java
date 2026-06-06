package net.vami.game.interactable.interaction.patrons;

import net.vami.game.interactable.entity.PlayerEntity;
import net.vami.game.interactable.interaction.abilities.Ability;
import net.vami.game.interactable.interaction.damagetypes.DamageType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public abstract class Patron {
    public static HashSet<Patron> patrons = new HashSet<>();

    public Patron() {
        patrons.add(this);
    }

    public abstract String name();

    public abstract int level();
    public abstract int maxHealth();
    public abstract int armor();
    public abstract int baseDamage();
    public abstract DamageType damageType();
    public abstract Ability ability();

    public void init(PlayerEntity player) {

    }

    public static Patron getRandomPatron() {
        return (Patron) patrons.toArray()[new Random().nextInt(patrons.size())];
    }

}
