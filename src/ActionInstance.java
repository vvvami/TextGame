public class ActionInstance extends Action {
    private Action action;
    private Entity source;
    private Entity target;
    private ObjectInteractable object;


    public ActionInstance(Action action, Entity source, Entity target) {
        this.action = action;
        this.source = source;
        this.target = target;

    }

    public ActionInstance(Action action, Entity source, ObjectInteractable object) {
        this.action = action;
        this.source = source;
        this.object = object;

    }

    public Entity getTarget() {
        return target;
    }

    public Entity getSource() {
        return source;
    }

    public ObjectInteractable getObject() {
        return object;
    }

}
