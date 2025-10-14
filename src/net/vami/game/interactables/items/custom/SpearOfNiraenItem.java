package net.vami.game.interactables.items.custom;

import net.vami.game.interactables.items.ItemHoldable;
import net.vami.game.interactables.items.UseableItem;
import net.vami.util.TextUtil;

public class SpearOfNiraenItem extends ItemHoldable implements UseableItem {
    private boolean isThrown = false;

    public SpearOfNiraenItem(String name, Attributes attributes) {
        super(name, attributes);
    }

    @Override
    public void onUse() {
        if (isThrown) {

        } else {
            isThrown = true;
            TextUtil.display("%s has thrown %s!");
        }
    }

    @Override
    public String getDisplayName() {
        return "Spear of Niraen";
    }
}
