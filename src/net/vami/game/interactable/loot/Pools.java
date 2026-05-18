package net.vami.game.interactable.loot;

import net.vami.game.interactable.item.Items;
import net.vami.game.interactable.item.attunement.Attunements;
import net.vami.game.interactable.item.custom.*;

public final class Pools {
    public static final LootPool BASIC_ITEMS = new LootPool()
            .add(Items.TEARS_OF_ARTHUUROS, 4)
            .add(Items.EYE_OF_ARTHUUROS, 4)
            .add(Items.VEXED_DOLL, 2);

    public static final LootPool WEAPONS = new LootPool()
            .add(Items.SPEAR_OF_NIRAEN, 4)
            .add(Items.KNIVES_OF_QALATH, 3)
            .add(Items.AMARAL, 1);

    public static final LootPool POTIONS = new LootPool()
            .add(Items.HOLY_SERUM, 1)
            .add(Items.BERSERK_SERUM, 1)
            .add(Items.THICKSKIN_SERUM, 1)
            .add(Items.CLEANSING_SERUM, 1);

    public static final LootPool ALTAR_ATTUNEMENTS = new LootPool()
            .add(Attunements.REINFORCED, 1)
            .add(Attunements.GLUTTON, 1);

}
