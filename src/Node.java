import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    private String description;
    private List<Object> objects;
    private final Position position;
    public static Set<Node> nodeMap = new HashSet<>();


    public Node(Position position) {
        this.position = position;
        nodeMap.add(this);
    }


    public void addObjectToNode(Object object) {
        this.objects.add(object);
    }

    public Position getNodePosition() {
        return this.position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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
