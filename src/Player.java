import java.util.Scanner;

public class Player extends Entity {


    public Player(String name, Position position, int level, int maxHealth, float baseDamage, int armor, Ability ability) {
        super(name, position, level, maxHealth, baseDamage, armor, DamageType.blunt, false, ability);

    }


}
