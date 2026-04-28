package net.vami.game.interactable.interaction.abilities;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.interactable.Interactable;

@JsonAdapter(AbilityAdapter.class)
public interface Ability {

    boolean useAbility(Interactable source, Interactable target);

    String getName();

    boolean isSupport();

    default boolean isSelfCast() {
        return false;
    }

    default boolean is(Ability ability) {
        return this.getClass() == ability.getClass();
    }
}