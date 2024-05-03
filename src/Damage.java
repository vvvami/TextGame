import java.util.ArrayList;
import java.util.List;

public class Damage {
    private static List<Damage> damages= new ArrayList<>();
    private String name;

    public Damage(String name) {
        damages.add(this);
        this.name = name;
    }

    public static final Damage blunt = new Damage("Blunt");
    public static final Damage sharp = new Damage("Sharp");
    public static final Damage fire = new Damage("Fire");
    public static final Damage ice = new Damage("Ice");
    public static final Damage bleed = new Damage("Bleed");

    public static List<Damage> getDamages() {
        return damages;
    }

    public String getName() {
        return name;
    }

}

