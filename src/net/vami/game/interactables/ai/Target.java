package net.vami.game.interactables.ai;

import net.vami.game.interactables.Interactable;

public class Target<K extends Class<Interactable>,I extends Interactable> {

    K klass;
    I interactable;

    public Target(K klass) {
        this.klass = klass;
    }

    public Target(I interactable) {
        this.interactable = interactable;
    }

}
