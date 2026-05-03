package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemBreakable;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;

public class BerserkSerumItem extends Item implements ItemUseable, ItemBreakable {

    public BerserkSerumItem() {
        super("Berserk Serum");
    }

    @Override
    public void onUse(Interactable source, ItemInstance item) {
        source.addStatus(new Status.Instance(
                Statuses.FRENZIED,
                1,
                3
        ));
        item.hurt(1);
    }

    @Override
    public int maxDurability() {
        return 1;
    }
}
