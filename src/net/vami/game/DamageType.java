package net.vami.game;
import net.vami.interactables.*;
import java.util.ArrayList;
import java.util.List;

public class DamageType {
    private static List<DamageType> damageTypes = new ArrayList<>();
    private String name;

    public DamageType(String name) {
        damageTypes.add(this);
        this.name = name;
    }

    public static final DamageType blunt = new DamageType("Blunt");
    public static final DamageType sharp = new DamageType("Sharp");
    public static final DamageType fire = new DamageType("Fire");
    public static final DamageType ice = new DamageType("Ice");
    public static final DamageType bleed = new DamageType("Bleed");

    public static List<DamageType> getDamages() {
        return damageTypes;
    }

    public String getName() {
        return name;
    }

}

