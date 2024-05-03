import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        GameInitializer.InitializeGame();

        Player.getPlayer().addStatus(new StatusInstance(Status.BLESSED,1,1, Player.getPlayer()));
    }
}