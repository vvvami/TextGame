package net.vami.game.interactables.ai;

import net.vami.game.interactables.items.equipables.ArthuurosEyeItem;
import net.vami.game.interactables.items.holdables.ExcaliburItem;
import net.vami.game.interactables.items.holdables.ItemHoldable;
import net.vami.game.interactables.items.useables.TearsOfArthuurosItem;
import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Werewolf;

public class EnemyHandler {

    public static void enemyAction(Node node) {
        for (Entity enemy : node.getEnemies()) {
            enemy.getBrain().selectTask(enemy);
        }
    }

    public static void Generate() {
        Werewolf werewolf = new Werewolf("Gang", new Entity.Attributes().level(1));
        werewolf.setHeldItem(new ExcaliburItem("Excalifur", new ItemHoldable.Attributes().baseDamage(1)));
        werewolf.addEquippedItem(new ArthuurosEyeItem("Eye of Arthuuros"));
        werewolf.addInventoryItem(new TearsOfArthuurosItem("Tears Of Arthuuros"));
        Entity.spawn(werewolf);
    }

}
