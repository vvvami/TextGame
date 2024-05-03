import java.util.ArrayList;
import java.util.List;

public class Damage {
    private static List<Damage> damages= new ArrayList<>();

    public Damage() {
        damages.add(this);
    }

    public static final Damage blunt = new Damage();
    public static final Damage sharp = new Damage();
    public static final Damage fire = new Damage();
    public static final Damage ice = new Damage();
    public static final Damage bleed = new Damage();

    public static List<Damage> getDamages() {
        return damages;
    }

}

