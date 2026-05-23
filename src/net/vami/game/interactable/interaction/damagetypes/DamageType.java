package net.vami.game.interactable.interaction.damagetypes;

import com.google.gson.annotations.JsonAdapter;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Hoverable;
import net.vami.game.interactable.Interactable;
import net.vami.game.world.DamageTypeAdapter;

@JsonAdapter(DamageTypeAdapter.class)
public abstract class DamageType implements Hoverable {

    public void onHit(Interactable target, Interactable source, float amount) {

    }

    public abstract String getName();

    public abstract Sound getSound();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)
                || this.getClass() == obj.getClass();
    }


    @Override
    public String getDisplayName() {
        return getName();
    }

    @Override
    public HoverInfo getHoverInfo() {
        return new HoverInfo(getDisplayName(), "A damage type.");
    }
}

