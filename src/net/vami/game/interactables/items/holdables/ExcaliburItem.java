package net.vami.game.interactables.items.holdables;

import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.items.BreakableItem;
import net.vami.game.interactables.items.useables.UseableItem;
import net.vami.game.world.Node;
import net.vami.util.TextUtil;
import org.fusesource.jansi.AnsiConsole;

public class ExcaliburItem extends ItemHoldable implements BreakableItem, UseableItem {
    public ExcaliburItem(String name, Attributes attributes) {
        super(name, attributes
                .damageType(new FireDamage())
                .baseDamage(15));
    }

    @Override
    public int maxDurability() {
        return 100;
    }

    @Override
    public void onUse() {
        Game.playSound(this.getDamageType().getSound(), 65);
        TextUtil.display(this.getOwner(),"%s blasts their surroundings with %s! %n", this.getOwner().getName(), this.getDisplayName());
        for (Entity entity : Node.getNodeFromPosition(this.getOwner().getPos()).getEntities()) {
            if (!(entity == this.getOwner())) {
                entity.hurt(this.getOwner(), this.getDamage(), this.getDamageType());
            }
        }
        this.hurt(5);
    }
}
