package net.vami.game.interactables.ai;

import net.vami.game.interactables.entities.Kalakuli;
import net.vami.game.interactables.interactions.abilities.FlamesAbility;
import net.vami.game.interactables.interactions.abilities.HypnosisAbility;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.interactions.damagetypes.IceDamage;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.equipables.ArthuurosEyeItem;
import net.vami.game.interactables.items.equipables.ArthuurosReapingArmband;
import net.vami.game.interactables.items.holdables.ExcaliburItem;
import net.vami.game.interactables.items.holdables.ItemHoldable;
import net.vami.game.interactables.items.useables.ExplorersMapItem;
import net.vami.game.interactables.items.useables.TearsOfArthuurosItem;
import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Wolf;
import net.vami.game.world.Position;

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

        Item.spawn(new ExcaliburItem("Excalibur", new ItemHoldable.Attributes().baseDamage(5)));
        Entity.spawn(new Wolf("Dagtha'ad", new Entity.Attributes().maxHealth(1)), true)
                .addEquippedItem(new ArthuurosEyeItem("Eye of Arthuuros"));
//        Entity.spawn(new Wolf("Irus", new Entity.Attributes().maxHealth(10)), true)
//                .addEquippedItem(new ArthuurosReapingArmband("Armband"));
        Entity.spawn(new Kalakuli("Katakuma", new Entity.Attributes()), true)
                .addInventoryItem(new TearsOfArthuurosItem("Tears of Arthuuros"));
    }

}
