package net.vami.game.interactable.interaction.action;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.display.Segmental;
import net.vami.game.display.sound.Sound;
import net.vami.game.display.sound.Sounds;
import net.vami.game.interactable.Interactable;
import net.vami.game.world.Node;
import net.vami.util.TextUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class ActionFeedback {
    ArrayList<String> feedbackList = new ArrayList<>();
    int maxVariables;

    public ActionFeedback(int maxVariables) {
        this.maxVariables = maxVariables;
    }


    public ActionFeedback addFeedbackOption(@NotNull ActionFeedbackType type, String string) {
        feedbackList.add(type.ordinal(), string);
        return this;
    }

    public ActionFeedback addFeedbackOption(int index, String string) {
        int length = ActionFeedbackType.values().length;
        feedbackList.add(Math.max(length + 1, length + index) , string);
        return this;
    }

    public void printFeedback(@NotNull ActionFeedbackType type, Sound sound, int volume, Object ... args) {
        Interactable source = Node.findNode(Game.player.getPos()).stringToInteractable(((Interactable) Arrays.stream(args).toList().getFirst()).getName());
        Display.display(source,
                new Segmental(feedbackList.get(type.ordinal()), args),
                sound, volume);
    }

    public void printFeedback(Sound sound, int volume, Object ... args) {
        ArrayList<Object> argsList = new ArrayList<>(Arrays.stream(args).distinct().toList());
        ActionFeedbackType feedbackType = ActionFeedbackType.NORMAL;

        if (argsList.contains(null) || argsList.contains("")) {
            argsList.remove("");
            feedbackType = ActionFeedbackType.GENERIC;
        } else if (argsList.size() < maxVariables) {
            feedbackType = ActionFeedbackType.SELF;
        }


        this.printFeedback(feedbackType, sound, volume, argsList.toArray());
    }

    public static final ActionFeedback HURT = new ActionFeedback(4)
            .addFeedbackOption(ActionFeedbackType.NORMAL, "%s was hit by %s for %s %s damage! %n")
            .addFeedbackOption(ActionFeedbackType.SELF, "%s hit themselves for %s %s damage! %n")
            .addFeedbackOption(ActionFeedbackType.GENERIC, "%s was hurt for %s %s damage! %n");

    public static final ActionFeedback HEAL = new ActionFeedback(3)
            .addFeedbackOption(ActionFeedbackType.NORMAL, "%s was healed by %s for %s health! %n")
            .addFeedbackOption(ActionFeedbackType.SELF, "%s healed themselves for %s health! %n")
            .addFeedbackOption(ActionFeedbackType.GENERIC, "%s was healed for %s health! %n");

    public static final ActionFeedback ABILITY = new ActionFeedback(3)
            .addFeedbackOption(ActionFeedbackType.NORMAL, "%s casts %s on %s! %n")
            .addFeedbackOption(ActionFeedbackType.SELF, "%s casts %s on themselves! %n")
            .addFeedbackOption(ActionFeedbackType.GENERIC, "%s casts %s! %n");
}
