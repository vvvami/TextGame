package net.vami.game.interactables.ai;

import net.vami.game.interactables.interactions.abilities.FlamesAbility;
import net.vami.game.interactables.interactions.abilities.HypnosisAbility;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.interactions.damagetypes.IceDamage;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.equipables.ArthuurosEyeItem;
import net.vami.game.interactables.items.equipables.ArthuurosReapingArmband;
import net.vami.game.interactables.items.holdables.ExcaliburItem;
import net.vami.game.interactables.items.holdables.ItemHoldable;
import net.vami.game.interactables.items.useables.TearsOfArthuurosItem;
import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Wolf;
import net.vami.game.world.Position;

public class EnemyHandler {

    public static void enemyAction(Node node) {
        for (Entity enemy : node.getEnemies()) {
            enemy.getBrain().selectTask(enemy);
        }
    }

    public static void Generate() {
        Entity.Attributes attributes = new Entity.Attributes();
        attributes
                .level(2)
                .ability(new FlamesAbility());

        Item.spawn(new ExcaliburItem("Il'thena", new ItemHoldable.Attributes()
                .baseDamage(5).damageType(new FireDamage())));
        Item.spawn(new TearsOfArthuurosItem("Tears of Arthuuros"));
        Item.spawn(new ArthuurosEyeItem("Eye of Arthuuros"));
        Entity.spawn(new Wolf("N'djell", new Entity.Attributes().level(1).ability(new HypnosisAbility())), false)
                .addInventoryItem(new TearsOfArthuurosItem("Tears of Arthuuros"));
        Entity.spawn(new Wolf("Friendly Wolf", attributes.level(attributes.getLevel() + 1)), new Position(-1,0,0), false);
        Entity.spawn(new Wolf("Wolf", attributes), new Position(1,0,0), true);
        Entity.spawn(new Wolf("Friendly Wolf", attributes.level(attributes.getLevel() + 1)), new Position(-1,0,0), false);
        Entity.spawn(new Wolf("Wolf", attributes.level(attributes.getLevel() + 1)), new Position(0,0,1), true);
        Entity.spawn(new Wolf("Friendly Wolf", attributes.level(attributes.getLevel() + 1)), new Position(0,0,-1), false);

    }

}
