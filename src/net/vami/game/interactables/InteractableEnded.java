package net.vami.game.interactables;
import net.vami.game.interactables.interactions.action.Action;


public class InteractableEnded extends Interactable {
    public InteractableEnded(String name) {

        super(name);
    }

    protected boolean receiveAttack(Interactable source, Action action) {
        return false;
    }

}
