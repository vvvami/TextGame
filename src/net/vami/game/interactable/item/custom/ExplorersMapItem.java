package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;
import net.vami.game.world.Direction;
import net.vami.game.world.Node;
import net.vami.game.world.Position;

public class ExplorersMapItem extends Item implements ItemUseable {
    public ExplorersMapItem(String name) {
        super(name);
    }

    public ExplorersMapItem() {
        this("Map");
    }

    @Override
    public void onUse(Interactable source, ItemInstance item) {
        Game.display(source, "%s is located at %s.%n", source.getDisplayName(), source.getPos().toString());
        Position position = source.getPos();
        for (Direction direction : Direction.values()) {
            if (Node.findNode(position.add(direction)) == null) {
                continue;
            }
            /*if (Node.getNodeFromPosition(position.add(direction)).getEntrances()
                    .contains(direction.getOpposite())) {
                Game.display(this.getOwner(), "You have an entrance available %sward. %n", direction.toString().toLowerCase());
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
