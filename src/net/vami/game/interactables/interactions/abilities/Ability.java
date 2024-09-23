package net.vami.game.interactables.interactions.abilities;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.interactables.Interactable;

@JsonAdapter(AbilityAdapter.class)
public interface Ability {

    boolean useAbility(Interactable source, Interactable target);

    String getName();

    boolean isSupport();

    default boolean is(Ability ability) {
        return this.getClass() == ability.getClass();
    }
}