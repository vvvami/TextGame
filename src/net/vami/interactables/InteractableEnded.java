package net.vami.interactables;
import net.vami.interactables.interactions.Action;


public class InteractableEnded extends Interactable {
    public InteractableEnded(String name) {

        super(name);
    }

    protected boolean receiveAttack(Interactable source, Action action) {
        return false;
    }

}
