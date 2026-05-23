package net.vami.game.world.room;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.custom.AttunementAltarInteractable;
import net.vami.game.interactable.custom.ChestInteractable;
import net.vami.game.interactable.loot.Pools;
import net.vami.game.world.Node;

public class AttunerRoom implements Room {

    @Override
    public void init(Node node) {
        AttunementAltarInteractable attuner = new AttunementAltarInteractable();
        Interactable.spawnInteractable(attuner, node.getPos());
    }

    @Override
    public void update(Node node) {

    }
}
