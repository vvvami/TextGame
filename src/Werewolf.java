public class Werewolf extends Entity{
    public Werewolf(String name, int level, int maxHealth, float baseDamage, int armor, boolean enemy) {
        super(name, level, maxHealth, baseDamage, armor, Damage.sharp, enemy);
    }

    public Werewolf(int level) {
        super("Werewolf", level, 20 * level, 3 * level, 2 * level, Damage.sharp, true);
    }
}
