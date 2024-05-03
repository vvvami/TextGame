import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    private List<Instance> instances;
    private List<Object> objects;
    private int xPos;
    private int yPos;
    private int zPos;
    private final Position position;
    private Set<Node> nodes = new HashSet<>();


    public Node(List<Instance> instances, List<Object> objects, Position position) {
        this.instances = instances;
        this.objects = objects;
        this.position = position;
        nodes.add(this);
    }

    public Set<Node> getNodes() {
        return nodes;
    }



}
