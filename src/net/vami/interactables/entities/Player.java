package net.vami.interactables.entities;
import net.vami.game.interactions.Ability;
import net.vami.game.interactions.Action;
import net.vami.game.interactions.DamageType;
import net.vami.game.world.Position;

public class Player extends Entity {

    public Player(String name, Position position, int level, int maxHealth, float baseDamage, int armor, Ability ability) {
        super(name, position, level, maxHealth, baseDamage, armor, DamageType.BLUNT, false, ability);
        addAvailableAction(Action.SAVE);
        addReceivableAction(Action.SAVE);
    }


}
