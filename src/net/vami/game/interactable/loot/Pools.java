package net.vami.game.interactable.loot;

import net.vami.game.interactable.item.Items;
import net.vami.game.interactable.item.custom.*;

public final class Pools {
    public static final LootPool BASIC_ITEMS = new LootPool()
            .add(Items.TEARS_OF_ARTHUUROS, 3)
            .add(Items.EYE_OF_ARTHUUROS, 20)
            .add(Items.VEXED_DOLL, 1);

    public static final LootPool WEAPONS = new LootPool()
            .add(Items.SPEAR_OF_NIRAEN, 10)
            .add(Items.KNIVES_OF_QALATH, 20)
            .add(Items.AMARAL, 5);

    public static final LootPool POTIONS = new LootPool()
            .add(Items.HOLY_SERUM, 1)
            .add(Items.BERSERK_SERUM, 1)
            .add(Items.THICKSKIN_SERUM, 1 )
            .add(Items.CLEANSING_SERUM, 1);

}
