package net.vami.game.interactables.interactions.modifier;

import java.util.List;

public class Modifier {
    ModifierType modifierType;
    String ID;
    float value;

    public Modifier(String ID, ModifierType modifierType, float value) {
        this.ID = ID;
        this.modifierType = modifierType;
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public ModifierType getAttributeType() {
        return modifierType;
    }

    public String getID() {
        return ID;
    }

    public static float calculate(List<Modifier> modifiers, ModifierType modType) {
        float sum = 0;
        for (Modifier modifier : modifiers) {
            if (modifier.modifierType == modType) {
                sum += modifier.value;
            }
        }
        return sum;
    }
}
