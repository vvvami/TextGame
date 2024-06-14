package net.vami.game.interactions;

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

