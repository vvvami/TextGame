package net.vami.game.world.room;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.entity.KalakuliEntity;
import net.vami.game.interactable.entity.WolfEntity;
import net.vami.game.world.Node;

public class BasicEnemyRoom implements Room {
    @Override
    public void init(Node node) {
        double chance = Math.random();
        if (chance < 0.13) {
            KalakuliEntity kalakuli = new KalakuliEntity("Kalakuli", new Entity.Attributes());
            Interactable.spawnInteractable(kalakuli, node.getPos());

        } else if (chance < 0.46) {
            WolfEntity wolf1 = new WolfEntity("Wolf1", new Entity.Attributes()
                    .level(Math.max(1, node.getPos().x() / 2
                            + node.getPos().y() / 2
                            + node.getPos().z() / 2)));
            WolfEntity wolf2 = new WolfEntity("Wolf2", new Entity.Attributes()
                    .level(Math.max(1, node.getPos().x() / 2
                            + node.getPos().y() / 2
                            + node.getPos().z() / 2)));

            Interactable.spawnInteractable(wolf1, node.getPos());
            Interactable.spawnInteractable(wolf2, node.getPos());

        } else {
            WolfEntity wolf = new WolfEntity("Wolf", new Entity.Attributes()
                    .level(Math.max(1, node.getPos().x()
                            + node.getPos().y()
                            + node.getPos().z())));

            Interactable.spawnInteractable(wolf, node.getPos());
        }
    }

    @Override
    public void update(Node node) {

    }
}
