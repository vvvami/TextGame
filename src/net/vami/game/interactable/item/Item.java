package net.vami.game.interactable.item;
import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.item.attunement.AttunableItem;
import net.vami.game.interactable.item.attunement.Attunement;
import net.vami.game.interactable.loot.Rollable;
import net.vami.util.TextUtil;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;

import java.awt.*;
import java.util.UUID;

public class Item implements Rollable {

    private String name;

    public Item(String name) {
        this.name = name;
    }

    public Item() {
        this("Item");
    }



    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || obj.getClass() == this.getClass();
    }

    public String getDisplayName() {
        return TextUtil.setColor(this.getName(), Color.magenta);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void turn(ItemInstance item) {

    }

    public ItemInstance create() {
        return new ItemInstance(this);
    }
}
