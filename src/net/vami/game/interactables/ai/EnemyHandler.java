package net.vami.game.interactables.ai;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.custom.AttunementAltar;
import net.vami.game.interactables.custom.Chest;
import net.vami.game.interactables.entities.Wolf;
import net.vami.game.interactables.items.custom.ArthuurosEyeItem;
import net.vami.game.interactables.items.custom.ArthuurosReapingArmband;
import net.vami.game.interactables.items.custom.ExcaliburItem;
import net.vami.game.interactables.items.ItemHoldable;
import net.vami.game.interactables.items.custom.KnivesOfQalath;
import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;

public class EnemyHandler {

    public static void enemyAction(Node node) {
        for (Entity enemy : node.getEnemies()) {
            if (enemy.getBrain() == null) {
                continue;
            }
            enemy.getBrain().selectTask(enemy);
        }
    }

    public static void Generate() {

        ItemHoldable excy = new ExcaliburItem("Excalibur", new ItemHoldable.Attributes().baseDamage(5));
        Interactable.spawn(new KnivesOfQalath("Knives", new ItemHoldable.Attributes()));
//        Entity.spawn(new Wolf("Dagtha'ad", new Entity.Attributes().maxHealth(10)), true)
//                .addEquippedItem(new ArthuurosEyeItem("Eye of Arthuuros"));
//        Entity.spawn(new Wolf("Irus", new Entity.Attributes().maxHealth(10)), true)
//                .addEquippedItem(new ArthuurosReapingArmband("Armband"));
        Interactable.spawn(new Chest("Chest").addToInventory(excy));
        Interactable.spawn(new AttunementAltar("Attuner"));

    }

}
