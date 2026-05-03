package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemBreakable;
import net.vami.game.interactable.item.ItemUseable;

public class ThickskinSerum extends Item implements ItemUseable, ItemBreakable {

    public ThickskinSerum() {
        super("Thickskin Serum");
    }

    @Override
    public void onUse(Interactable source) {
        source.addStatus(new Status.Instance(
                Statuses.ARMORED,
                1,
                3
        ));
        this.hurt(1);
    }

    @Override
    public int maxDurability() {
        return 1;
    }
}
