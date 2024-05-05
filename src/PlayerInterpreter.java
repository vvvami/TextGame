import java.util.Scanner;

public class PlayerInterpreter {

    public static boolean read() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your next action: ");
        String fullAction = scanner.nextLine();
        String actionTarget = actionInput(fullAction);

        for (Entity entity : Game.entityList) {
            if (entity.getName().equalsIgnoreCase(actionTarget)) {
                Action.entityAction(entity, Game.player);
                return true;
            }
        }

        for (ObjectInteractable object : Game.objectInteractables) {
            if (object.getName().equalsIgnoreCase(actionTarget)) {
                Action.entityAction(object, Game.player);
                return true;
            }
        }
        return false;
    }


    private static String actionInput(String input) {
        for (Action action : Action.actions) {
            for (String keyword : action.getSynonyms()) {
                if (input.contains(keyword)) {
                    Game.player.setAction(action);
                    input = input.substring(input.indexOf(" ") + 1);
                    return input;
                }
            }
        }
        return null;
    }
}
