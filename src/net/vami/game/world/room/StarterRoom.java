package net.vami.game.world.room;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.custom.AttunementAltarInteractable;
import net.vami.game.interactable.custom.ChestInteractable;
import net.vami.game.interactable.loot.Pools;
import net.vami.game.world.Node;

import java.util.Random;

public class StarterRoom implements Room {

    @Override
    public void init(Node node) {
        ChestInteractable chest = new ChestInteractable("Chest");
        chest.roll(Pools.WEAPONS, 10);
        Interactable.spawnInteractable(chest, node.getPos());

        AttunementAltarInteractable attuner = new AttunementAltarInteractable("Attuner");
        Interactable.spawnInteractable(attuner, node.getPos());
    }

    @Override
    public void update(Node node) {

    }
}
