package net.vami.interactables.entities;
import net.vami.game.interactions.Action;
import net.vami.game.world.Position;

public class Player extends Entity {

    public Player(String name, Attributes attributes) {
        super(name, attributes
                .enemy(false)
                .maxHealth(attributes.levelAttribute * 10)
                .armor((int) (attributes.levelAttribute * 1.5f))
                .baseDamage(attributes.levelAttribute));

        addAvailableAction(Action.SAVE);
        addReceivableAction(Action.SAVE);
    }
}
