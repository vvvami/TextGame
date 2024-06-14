package net.vami.interactables.entities;
import net.vami.game.interactions.Action;
import net.vami.game.world.Position;

public class Player extends Entity {

    public Player(String name, Attributes attributes) {
        super(name, attributes);
        addAvailableAction(Action.SAVE);
        addReceivableAction(Action.SAVE);
    }
}
