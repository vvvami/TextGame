import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class Interactable {
    private boolean equipped;
    private final UUID ID;
    private final String name;
    private Position position;
    private String description;
    private List<Action> receivableActions = new ArrayList<>();
    private List<Action> availableActions = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Interactable(String name, String description, Position position) {
        this.name = name;
        this.description = description;
        this.position = position;
        ID = UUID.randomUUID();

    }

    public UUID getID() {
        return ID;
    }

    public boolean receiveAction(Interactable source, Action action) {
        if (action == Action.attack) {

        }
        return true;
    }

    public boolean applyAction(Interactable target, Action action) {
        return false;
    }

    public void kill() {
        setPosition(null);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        if (position == this.position) {
            return;
        }
        Node.getNodeFromPosition(this.position).removeInteractable(this);
        this.position = position;
        Node node = Node.getNodeFromPosition(this.position);
        if (node != null) {
            node.addInteractable(this);
        }

    }
}
