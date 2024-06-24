package net.vami.interactables.interactions.abilities;

import net.vami.interactables.Interactable;

import java.util.HashMap;

public class Ability {
    private final String name;
    private static HashMap<String, Ability> ABILITIES = new HashMap<>();



    protected Ability() {
        name = this.getClass().getSimpleName().replace("Ability", "");
        if (ABILITIES.containsKey(name) || name.isEmpty()) {
            try {
                throw new Exception("Ability instantiation error");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ABILITIES.put(name, this);
    }

    public boolean useAbility(Interactable source, Interactable target) {

        return false;
    }

    public void beforeAbilityUsed(Interactable source, Interactable target) {

    }

    public static Ability getAbility(String name) {
        name = name.toLowerCase();
        return ABILITIES.get(name.toLowerCase());
    }

    public String getName() {
        return name;
    }

}
