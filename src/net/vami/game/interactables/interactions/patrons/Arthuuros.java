package net.vami.game.interactables.interactions.patrons;

import com.google.gson.Gson;
import net.vami.game.interactables.interactions.abilities.Ability;
import net.vami.game.interactables.interactions.abilities.RageAbility;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;

import java.io.FileWriter;
import java.io.IOException;

public class Arthuuros implements Patron {
    @Override
    public String name() {
        return "Arthuuros";
    }

    @Override
    public int level() {
        return 2;
    }

    @Override
    public int maxHealth() {
        return 3;
    }

    @Override
    public int armor() {
        return 2;
    }

    @Override
    public int baseDamage() {
        return 5;
    }

    @Override
    public DamageType damageType() {
        return new SharpDamage();
    }

    @Override
    public Ability ability() {
        return new RageAbility();
    }
}
