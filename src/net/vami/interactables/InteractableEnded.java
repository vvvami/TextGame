package net.vami.interactables;
import net.vami.game.*;


public class InteractableEnded extends Interactable {
    public InteractableEnded(String name, String description, Position position) {
        super(name, description, position);
    }

    protected boolean receiveAttack(Interactable source, Action action) {
        return false;
    }

}
