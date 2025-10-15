package net.vami.game.interactables.ai;

import net.vami.game.Game;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.custom.AttunementAltar;
import net.vami.game.interactables.custom.Chest;
import net.vami.game.interactables.entities.WolfEntity;
import net.vami.game.interactables.items.custom.ExcaliburItem;
import net.vami.game.interactables.items.ItemHoldable;
import net.vami.game.interactables.items.custom.KnivesOfQalath;
import net.vami.game.interactables.items.custom.SpearOfNiraenItem;
import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.world.Position;

public class EnemyHandler {

    public static void enemyAction(Node node) {
        for (Entity enemy : node.getEntities()) {
            if (enemy.isFriendlyTo(Game.player)
                    || enemy.getBrain() == null) {
                continue;
            }
            enemy.getBrain().selectTask(enemy);
        }
    }

    public static void Generate() {

        ItemHoldable excalibur = new ExcaliburItem();
        Interactable.spawnInteractable(new KnivesOfQalath());
        Interactable.spawnInteractable(new SpearOfNiraenItem(), new Position(0,0,0));
        Interactable.spawnInteractable(new WolfEntity("Wolf1", new Entity.Attributes()));
//        Interactable.spawnInteractable(new WolfEntity("Wolf2", new Entity.Attributes()));
//        Interactable.spawnInteractable(new WolfEntity("Wolf3", new Entity.Attributes()));

//        Entity.spawn(new Wolf("Dagtha'ad", new Entity.Attributes().maxHealth(10)), true)
//                .addEquippedItem(new ArthuurosEyeItem("Eye of Arthuuros"));
//        Entity.spawn(new Wolf("Irus", new Entity.Attributes().maxHealth(10)), true)
//                .addEquippedItem(new ArthuurosReapingArmband("Armband"));
        Interactable.spawnInteractable(new Chest("Chest").addToInventory(excalibur));
        Interactable.spawnInteractable(new AttunementAltar("Attuner"));

    }

}
