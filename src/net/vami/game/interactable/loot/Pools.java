package net.vami.game.interactable.loot;

import net.vami.game.interactable.item.custom.*;

public final class Pools {
    public static final LootPool BASIC_ITEMS = new LootPool()
            .add(new TearsOfArthuurosItem(), 3)
            .add(new EyeOfArthuurosItem(), 20)
            .add(new VexedDollItem(), 1);

    public static final LootPool WEAPONS = new LootPool()
            .add(new SpearOfNiraenItem(), 10)
            .add(new KnivesOfQalathItem(), 20)
            .add(new AmaralItem(), 5);

}
