package net.vami.interactables.interactions;

public enum DamageType {

    NONE("None"),
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

