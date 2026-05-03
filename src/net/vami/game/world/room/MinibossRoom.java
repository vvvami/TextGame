package net.vami.game.world.room;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.custom.ChestInteractable;
import net.vami.game.interactable.entity.AngelEntity;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.entity.KalakuliEntity;
import net.vami.game.interactable.entity.WolfEntity;
import net.vami.game.interactable.loot.Pools;
import net.vami.game.world.Node;

import java.util.Random;

public class MinibossRoom implements Room {
    @Override
    public void init(Node node) {

        AngelEntity angel = new AngelEntity();
        Interactable.spawnInteractable(angel, node.getPos());
    }

    @Override
    public void update(Node node) {

    }
}
