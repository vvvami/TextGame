public class Werewolf extends Entity{
    public Werewolf(String name, int level, int maxHealth, float baseDamage, int armor, boolean enemy, Ability ability) {
        super(name, level, maxHealth, baseDamage, armor, DamageType.sharp, enemy, ability);
    }

    public Werewolf(int level) {
        super("Werewolf", level, 20 * level, 3 * level,
                2 * level, DamageType.sharp, true, Ability.WOUND);
    }
}
