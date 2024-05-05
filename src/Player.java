import java.util.Scanner;

public class Player extends Entity {


    private static Node node;
    private static ItemObject equippedItem;

    public Player(String name, int level, int maxHealth, float baseDamage, int armor, Ability ability) {
        super(name, level, maxHealth, baseDamage, armor, DamageType.blunt, false, ability);

    }

    public static Node getPlayerNode() {
        return node;
    }

    public static void setPlayerNode(Node node) {
        Player.node = node;
    }

    public static Position getPlayerPosition() {
        return Player.node.getNodePosition();
    }

    public static void setPlayerPosition(Position position) {
        Player.setPlayerNode(position.getNodeFromPosition());
    }

}
