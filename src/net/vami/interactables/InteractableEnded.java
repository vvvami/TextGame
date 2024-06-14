package net.vami.interactables;
import net.vami.game.interactions.Action;
import net.vami.game.world.Position;


public class InteractableEnded extends Interactable {
    public InteractableEnded(String name) {

        super(name);
    }

    protected boolean receiveAttack(Interactable source, Action action) {
        return false;
    }

}
