package net.vami.game.interactables.items.useables;

import net.vami.game.Game;
import net.vami.game.interactables.items.Item;
import net.vami.game.world.Direction;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.util.TextUtil;

import java.util.ArrayList;

public class ExplorersMapItem extends Item implements UseableItem {
    public ExplorersMapItem(String name) {
        super(name);
    }

    @Override
    public void onUse() {
        TextUtil.display(this.getOwner(), "You are located at %s.%n", this.getOwner().getPos().toString());
        Position position = this.getOwner().getPos();
        for (Direction direction : Direction.values()) {
            if (Node.getNodeFromPosition(position.add(direction)) == null) {
                continue;
            }
            if (Node.getNodeFromPosition(position.add(direction)).getEntrances()
                    .contains(direction.getOpposite())) {
                TextUtil.display(this.getOwner(), "You have an entrance available %sward. %n", direction.toString().toLowerCase());
            }
        }
    }
}
