package net.vami.game.interactable.interaction.abilities;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.interactable.Interactable;

import java.util.ArrayList;

@JsonAdapter(AbilityAdapter.class)
public abstract class Ability {
    private static ArrayList<Ability> abilities = new ArrayList<>();

    public Ability() {
        abilities.add(this);
    }

    abstract public boolean useAbility(Interactable source, Interactable target);

    abstract public String getName();

    abstract public boolean isSupport();

    public boolean isSelfCast() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || obj.getClass() == this.getClass();
    }

    public static ArrayList<Ability> registry() {
        return abilities;
    }
}