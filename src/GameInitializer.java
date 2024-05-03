import java.util.Scanner;

public class GameInitializer {

    public static void InitializeGame() {
        Action.actionSynonymInitializer();

        System.out.println("Enter your name, traveler:");
        Player.getPlayer();

    }

}
