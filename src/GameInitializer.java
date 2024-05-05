import java.util.Scanner;

public abstract class GameInitializer {

    public static void InitializeGame() throws Exception {
        Action.actionSynonymInitializer();
        Node.initializeNodes();
    }

}
