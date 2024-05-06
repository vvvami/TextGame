public class ActionInstance extends Action {
    private Action action;
    private Entity source;
    private Entity target;
    private Interactable object;


    public ActionInstance(Action action, Entity source, Entity target) {
        this.action = action;
        this.source = source;
        this.target = target;

    }

    public ActionInstance(Action action, Entity source, Interactable object) {
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

    public Interactable getObject() {
        return object;
    }

}
