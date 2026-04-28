package net.vami.game.interactable;
import net.vami.game.interactable.interaction.action.Action;


public class InteractableEnded extends Interactable {
    public InteractableEnded(String name) {

        super(name);
    }

    protected boolean receiveAttack(Interactable source, Action action) {
        return false;
    }

}
