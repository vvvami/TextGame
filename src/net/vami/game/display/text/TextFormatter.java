package net.vami.game.display.text;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.interactions.statuses.Status;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.text.DecimalFormat;

public class TextFormatter {

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
            AnsiConsole.out.printf("%s was hit by %s for %s %s damage! %n", interaction.target.getDisplayName(),
                    interaction.source.getDisplayName(),
                    TextFormatter.yellow(new DecimalFormat("##.##").format(interaction.amount)),
                    interaction.damageType.getName());
        }

        public static void healEntity(EntityInteraction interaction) {
            String stringAmount = TextFormatter.yellow(new DecimalFormat("##.##").format(interaction.amount));
            AnsiConsole.out.printf("%s was healed by %s for %s health! %n", interaction.target.getDisplayName(),
                    interaction.source.getDisplayName(), stringAmount);
        }
    }

    public static String blue(String input) {
        return ANSI_BLUE + input + ANSI_RESET;
    }

    public static String red(String input) {
        return ANSI_RED + input + ANSI_RESET;
    }

    public static String green(String input) {
        return ANSI_GREEN + input + ANSI_RESET;
    }

    public static String yellow(String input) {
        return ANSI_YELLOW + input + ANSI_RESET;
    }

    public static String purple(String input) {
        return ANSI_PURPLE + input + ANSI_RESET;
    }

    public static String cyan(String input) {
        return ANSI_CYAN + input + ANSI_RESET;
    }



    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
}
