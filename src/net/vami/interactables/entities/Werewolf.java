package net.vami.interactables.entities;
import net.vami.game.*;

public class Werewolf extends Entity {
    public Werewolf(String name, Position position, int level, int maxHealth, float baseDamage, int armor, boolean enemy, Ability ability) {
        super(name, position, level, maxHealth, baseDamage, armor, DamageType.sharp, enemy, ability);
    }

    public Werewolf(Position position, int level) {
        super("Werewolf", position, level, 20 * level, 3 * level,
                2 * level, DamageType.sharp, true, Ability.WOUND);
    }
}
