package net.vami.game.interactable.interaction.abilities;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.interactable.Hoverable;
import net.vami.game.interactable.Interactable;
import net.vami.util.TextUtil;

import java.awt.*;
import java.util.ArrayList;

@JsonAdapter(AbilityAdapter.class)
public abstract class Ability implements Hoverable {
    private static ArrayList<Ability> abilities = new ArrayList<>();

    public Ability() {
        abilities.add(this);
    }

    abstract public boolean useAbility(Interactable source, Interactable target);

    abstract public String getName();

    abstract public boolean isSupport();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || obj.getClass() == this.getClass();
    }

    public static ArrayList<Ability> registry() {
        return abilities;
    }

    @Override
    public String getDisplayName() {
        return TextUtil.setColor(getName(), Color.cyan);
    }

    @Override
    public HoverInfo getHoverInfo() {
        return new HoverInfo(getDisplayName(), "A unique ability.");
    }
}