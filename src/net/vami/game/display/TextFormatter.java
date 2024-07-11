package net.vami.game.display;

import net.vami.game.TextGame;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.interactions.DamageType;
import net.vami.interactables.interactions.statuses.Status;

import java.text.DecimalFormat;

public class TextFormatter {


    protected static String formatName(Status status) {
        String statusName;
        if (status.isHarmful()) {
            statusName = ANSI_RED + status.getName() + ANSI_RESET;
        } else {
            statusName = ANSI_GREEN + status.getName() + ANSI_RESET;
        }
        return statusName;
    }



    public static class EntityInteraction {
        Entity target;
        Entity source;
        float amount;
        DamageType damageType;

        public EntityInteraction(Entity target, Entity source, float amount, DamageType damageType) {
            this.target = target;
            this.source = source;
            this.amount = amount;
            this.damageType = damageType;
        }

        public EntityInteraction(Entity target, Entity source, float amount) {
            this.target = target;
            this.source = source;
            this.amount = amount;
        }



        public static void hurtEntity(EntityInteraction interaction) {
            System.out.printf("%s suffered %s damage! %n", interaction.target.getDisplayName(), ANSI_YELLOW
                    + new DecimalFormat("##.##").format(interaction.amount)
                    + ANSI_RESET + " " + interaction.damageType.getName());
        }

        public static void healEntity(EntityInteraction interaction) {
            String stringAmount = TextFormatter.ANSI_YELLOW + interaction.amount + TextFormatter.ANSI_RESET;
            System.out.printf("%s was healed by %s for %s health! %n", interaction.target.getDisplayName(),
                    interaction.source.getDisplayName(), stringAmount);
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
