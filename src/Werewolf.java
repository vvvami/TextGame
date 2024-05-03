public class Werewolf extends Entity{
    public Werewolf(String name, int maxHealth, float baseDamage, int armor, boolean enemy) {
        super(name, maxHealth, baseDamage, armor, Damage.sharp, enemy);
    }

    public Werewolf() {
        super("Werewolf", 100, 10, 5, Damage.sharp, true);
    }
}
