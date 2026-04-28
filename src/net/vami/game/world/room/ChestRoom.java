package net.vami.game.world.room;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.custom.ChestInteractable;
import net.vami.game.interactable.loot.Pools;
import net.vami.game.world.Node;

import java.util.Random;

public class ChestRoom implements Room {

    @Override
    public void init(Node node) {
        ChestInteractable chest = new ChestInteractable("Chest");
        if (new Random().nextBoolean()) {
            chest.roll(Pools.BASIC_ITEMS, 1);
        } else {
            chest.roll(Pools.WEAPONS, 1);
        }
        Interactable.spawnInteractable(chest, node.getPos());
    }

    @Override
    public void update(Node node) {

    }
}
