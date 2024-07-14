package net.vami.interactables.entities;
import net.vami.interactables.Interactable;
import net.vami.interactables.ai.Brain;
import net.vami.interactables.interactions.Action;

import java.io.*;

public class Player extends Entity implements Serializable {

    private Entity patron;

    public Player(String name, Attributes attributes) {
        super(name, attributes
                .maxHealth(attributes.levelAttribute * 10)
                .armor((int) (attributes.levelAttribute * 1.5f))
                .baseDamage(attributes.levelAttribute));

        addAvailableAction(Action.SAVE);
        addReceivableAction(Action.SAVE);
    }

    @Override
    public Brain getBrain() {
        return null;
    }

    @Override
    public boolean receiveSave(Interactable source) {

        return super.receiveSave(source);
    }

}
