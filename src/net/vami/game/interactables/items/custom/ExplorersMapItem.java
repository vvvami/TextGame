package net.vami.game.interactables.items.custom;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.UseableItem;
import net.vami.game.world.Direction;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.util.TextUtil;

public class ExplorersMapItem extends Item implements UseableItem {
    public ExplorersMapItem(String name) {
        super(name);
    }

    public ExplorersMapItem() {
        this("Map");
    }

    @Override
    public void onUse() {
        TextUtil.display(this.getOwner(), "You are located at %s.%n", this.getOwner().getPos().toString());
        Position position = this.getOwner().getPos();
        for (Direction direction : Direction.values()) {
            if (Node.findNode(position.add(direction)) == null) {
                continue;
            }
            /*if (Node.getNodeFromPosition(position.add(direction)).getEntrances()
                    .contains(direction.getOpposite())) {
                TextUtil.display(this.getOwner(), "You have an entrance available %sward. %n", direction.toString().toLowerCase());
            }*/

        }

        for (Node node : Node.getNodeMap().values()) {
            if (!node.getInteractables().isEmpty()) {
                for (Interactable interactable : node.getInteractables()) {
                    System.out.println(interactable.getName() + " " + interactable.getPos().toString());
                }
            }
        }
    }
}
