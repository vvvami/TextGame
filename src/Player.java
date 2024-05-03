import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Player extends Entity {
    private static final Scanner playerNameScanner = new Scanner(System.in);
    private static final String playerName = playerNameScanner.nextLine();
    private static final Player player = new Player(playerName, 1, 100, 1000, 0);

    private Position position;

    public Player(String name, int level, int maxHealth, float baseDamage, int armor) {
        super(name, level, maxHealth, baseDamage, armor, Damage.blunt, false);

    }

    public static Player getPlayer() {
        return player;
    }


}
