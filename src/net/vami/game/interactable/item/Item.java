package net.vami.game.interactable.item;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.interactable.Hoverable;
import net.vami.game.interactable.loot.Rollable;
import net.vami.util.TextUtil;

import java.awt.*;

public class Item implements Rollable, Hoverable {

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

    @Override
    public HoverInfo getHoverInfo() {
        return new HoverInfo(this.getDisplayName(),
                "An item that does stuff");
    }

    @Override
    public String getDisplayName() {
        return TextUtil.setColor(getName(), Color.magenta);
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
