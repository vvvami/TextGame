package net.vami.game.interactables.interactions.patrons;

import com.google.gson.Gson;
import net.vami.game.interactables.interactions.abilities.Ability;
import net.vami.game.interactables.interactions.abilities.RageAbility;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.equipables.ArthuurosEyeItem;
import net.vami.game.interactables.items.equipables.ItemEquipable;
import net.vami.game.interactables.items.holdables.ItemHoldable;
import net.vami.game.interactables.items.useables.TearsOfArthuurosItem;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.io.IOException;
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
        return new SharpDamage();
    }

    @Override
    public Ability ability() {
        return new RageAbility();
    }

    @Override
    public ArrayList<Item> defaultItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new TearsOfArthuurosItem("Tears of Arthuuros"));
        items.add(new ArthuurosEyeItem("Eye of Arthuuros"));
        return items;
    }
}
