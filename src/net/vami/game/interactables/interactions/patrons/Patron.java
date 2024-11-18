package net.vami.game.interactables.interactions.patrons;

import net.vami.game.interactables.interactions.abilities.Ability;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.ItemEquipable;
import net.vami.game.interactables.items.ItemHoldable;

import java.util.ArrayList;

public interface Patron {
    String name();

    int level();
    int maxHealth();
    int armor();
    int baseDamage();
    DamageType damageType();
    Ability ability();

    default ItemHoldable defaultHoldable() {
        return null;
    }
    default ArrayList<ItemEquipable> defaultEquipables() {
        return new ArrayList<>();
    }
    default ArrayList<Item> defaultItems() {
        return new ArrayList<>();
    }

}
