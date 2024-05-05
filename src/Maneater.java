public class Maneater extends Entity{
    public Maneater(String name, int level, int maxHealth, float baseDamage, int armor, boolean enemy, Ability ability) {
        super(name, level, maxHealth, baseDamage, armor, DamageType.sharp, enemy, ability);
    }

    public Maneater(int level) {

        super("Maneater", level, 10 * level, 4 * level,
                5 * level, DamageType.sharp, true, Ability.FREEZE);
    }
}
