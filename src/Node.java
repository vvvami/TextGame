import java.util.*;

public class Node {
    private String description;
    private final Position position;
    private List<Interactable> interactables = new ArrayList<>();
    private static HashMap<Position, Node> nodeMap = new HashMap<>();


    public Node(Position position) {
        this.position = position;
        nodeMap.put(position, this);
    }


    public Position getNodePosition() {
        return position;
    }

    public static Node getNodeFromPosition(Position pos) {
        return Node.nodeMap.get(pos);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<Interactable> getInteractables() {
        return interactables;
    }

    public void addInteractable(Interactable interactable) {
        interactables.add(interactable);
    }

    public void removeInteractable(Interactable interactable) {
        interactables.remove(interactable);
    }

    public Interactable stringToInteractable(String name) {
        for (Interactable interactable : interactables) {
            if (name.equals(interactable.getName())) {
                return interactable;
            }
        }
        return null;
    }

    public static void initializeNodes() {
        int size = 50;
        for (int h = -50; h <= 50; h++) {

        }
            for (int j = -size; j <= size; j++) {
                for (int i = -size; i <= size; i++) {
                new Node(new Position(i, 0, j));
                }
            }

    }


}
