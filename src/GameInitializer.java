import java.util.Scanner;

public abstract class GameInitializer {

    public static void InitializeGame() {
        Action.actionSynonymInitializer();
        Node.initializeNodes();
    }

}
