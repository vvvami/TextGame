package net.vami.game.interactables.interactions.patrons;

import net.vami.game.interactables.interactions.abilities.Ability;
import net.vami.game.interactables.interactions.abilities.RageAbility;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.custom.ArthuurosEyeItem;
import net.vami.game.interactables.items.custom.TearsOfArthuurosItem;

import java.util.ArrayList;

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
        return SharpDamage.get;
    }

    @Override
    public Ability ability() {
        return RageAbility.get;
    }

    @Override
    public ArrayList<Item> defaultItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new TearsOfArthuurosItem());
        items.add(new ArthuurosEyeItem());
        return items;
    }
}
