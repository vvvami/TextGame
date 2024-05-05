import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Action {
    private List<String> synonyms;
    public static List<Action> actions = new ArrayList<>();

    public Action() {
        this.synonyms = new ArrayList<>();
        actions.add(this);
    }

    public List<String> getSynonyms(){
        return synonyms;
    }

    public void addSynonym(String synonym) {
        synonyms.add(synonym);
    }

    public static final Action attack = new Action();
    public static final Action move = new Action();
    public static final Action use = new Action();
    public static final Action take = new Action();
    public static final Action equip = new Action();
    public static final Action ability = new Action();

    public static void actionSynonymInitializer() {
        Action.attackSynonymInitializer();
        Action.moveSynonymInitializer();
        Action.useSynonymInitializer();
        Action.abilitySynonymInitializer();
        Action.takeSynonymInitializer();
    }

    private static void attackSynonymInitializer() {
        attack.addSynonym("attack");
        attack.addSynonym("strike");
        attack.addSynonym("kick");
        attack.addSynonym("punch");
        attack.addSynonym("hurt");
        attack.addSynonym("hit");
        attack.addSynonym("kill");
    }

    private static void moveSynonymInitializer() {
        move.addSynonym("move");
        move.addSynonym("walk");
        move.addSynonym("run");
        move.addSynonym("escape");
        move.addSynonym("flee");
        move.addSynonym("go");
    }

    private static void useSynonymInitializer() {
        use.addSynonym("use");
        use.addSynonym("utilize");
        use.addSynonym("activate");
        use.addSynonym("drink");
        use.addSynonym("eat");
    }

    private static void abilitySynonymInitializer() {
        ability.addSynonym("ability");
        ability.addSynonym("cast");
        ability.addSynonym("draw");
        ability.addSynonym("conjure");
    }

    private static void takeSynonymInitializer() {
        take.addSynonym("take");
        take.addSynonym("steal");
        take.addSynonym("grab");
        take.addSynonym("pocket");
        take.addSynonym("inventory");
    }

    public static void entityAction(Entity target, Entity source) {
        if (source.getAction() == Action.attack) {
            attackAction(target, source);
        }
        else if (source.getAction() == Action.ability) {
            abilityAction(target, source);
        }
    }

    public static void entityAction(Object objectTarget, Entity source) {
        if (source.getAction() == Action.use) {

        }
        else if (source.getAction() == Action.equip) {

        }
    }

    public static void attackAction(Entity target, Entity source) {
        float damage = source.getBaseDamage();
        DamageType type = source.getDefaultDamageType();

        if (source.hasEquippedItem()) {
            damage += source.getEquippedItem().getDamageAmount();
            type = source.getEquippedItem().getDamageType();
        }

        System.out.printf("%s was hit by %s! %n", target.getDisplayName(), source.getDisplayName());
        target.hurt(source, damage, type);
    }

    public static void abilityAction(Entity target, Entity source) {
        System.out.println(source.getDisplayName() + " casts " + source.getAbility().getName()
                + " on " + target.getDisplayName() + "!");
        new AbilityInstance(source.getAbility(), target, source);
    }

}
