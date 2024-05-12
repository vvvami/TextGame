package net.vami.interactables;
import net.vami.game.*;

public class Maneater extends Entity{
    public Maneater(String name, Position position, int level, int maxHealth, float baseDamage, int armor, boolean enemy, Ability ability) {
        super(name, position, level, maxHealth, baseDamage, armor, DamageType.sharp, enemy, ability);
    }

    public Maneater(Position position, int level) {

        super("Maneater", position, level, 10 * level, 4 * level,
                5 * level, DamageType.sharp, true, Ability.FREEZE);
    }
}
