package net.vami.interactables.ai;

import net.vami.game.world.Node;
import net.vami.interactables.Interactable;
import net.vami.interactables.InteractableEnded;

public interface IActionable {
    boolean receiveAttack(Interactable source);
    boolean receiveAbility(Interactable source);
    boolean receiveEquip(Interactable source);
    boolean receiveMovement(Interactable source);
    boolean receiveTake(Interactable source);
    boolean receiveSave(Interactable source);
}
