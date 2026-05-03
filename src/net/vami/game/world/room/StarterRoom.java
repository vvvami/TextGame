package net.vami.game.world.room;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.custom.ChestInteractable;
import net.vami.game.interactable.loot.Pools;
import net.vami.game.world.Node;

import java.util.Random;

public class StarterRoom implements Room {

    @Override
    public void init(Node node) {
        ChestInteractable chest = new ChestInteractable("Chest");
        chest.roll(Pools.POTIONS, 1);
        Interactable.spawnInteractable(chest, node.getPos());
    }

    @Override
    public void update(Node node) {

    }
}
