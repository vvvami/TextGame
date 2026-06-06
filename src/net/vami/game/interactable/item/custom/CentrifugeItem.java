package net.vami.game.interactable.item.custom;

import net.vami.game.display.Display;
import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemEquipable;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.loot.Pools;

public class CentrifugeItem extends ItemEquipable {
    public CentrifugeItem() {
        super("Centrifuge");
    }

    @Override
    public void turn(ItemInstance item) {
        if (!item.getOwner().hasTarget()) return;

        item.addData("test", 0);

        int count = item.getData("test").asInt();

        count++;
        if (count == 3) {
            ItemInstance potion = ((Item) Pools.POTIONS.choose()).create();
             item.getOwner().addInventoryItem(potion);
            Display.showText("%s grants you %s.%n", item, potion);
             count = 0;
        }

        item.setData("test", count);
    }
}
