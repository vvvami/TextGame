package net.vami.interactables.entities;
import net.vami.game.*;

public class Player extends Entity {

    public Player(String name, Position position, int level, int maxHealth, float baseDamage, int armor, Ability ability) {
        super(name, position, level, maxHealth, baseDamage, armor, DamageType.BLUNT, false, ability);
        addAvailableAction(Action.SAVE);
        addReceivableAction(Action.SAVE);
    }


}
