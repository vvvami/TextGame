package net.vami.interactables;
import net.vami.game.interactions.Action;
import net.vami.game.world.Position;


public class InteractableEnded extends Interactable {
    public InteractableEnded(String name, String description, Position position) {
        super(name, description, position);
    }

    protected boolean receiveAttack(Interactable source, Action action) {
        return false;
    }

}
