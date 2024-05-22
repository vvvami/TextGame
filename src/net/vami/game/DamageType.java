package net.vami.game;
import net.vami.interactables.*;
import java.util.ArrayList;
import java.util.List;

public enum DamageType {

    BLUNT("Blunt"),
    SHARP("Sharp"),
    FIRE("Fire"),
    ICE("Ice"),
    BLEED("Bleed");

    private String name;

    DamageType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}

