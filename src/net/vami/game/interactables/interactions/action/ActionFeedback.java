package net.vami.game.interactables.interactions.action;

import net.vami.game.Game;
import net.vami.game.interactables.Interactable;
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

    public void printFeedback(int option, Object ... args) {
        TextUtil.display(feedbackList.get(option), args);
    }

    public void printFeedback(@NotNull ActionFeedbackType type, Object ... args) {
        Interactable source = Node.findNode(Game.player.getPos()).stringToInteractable((String) Arrays.stream(args).toList().getFirst());
        TextUtil.display(source, feedbackList.get(type.ordinal()), args);
    }

    public void printFeedback(Object ... args) {
        ArrayList<Object> argsList = new ArrayList<>(Arrays.stream(args).distinct().toList());
        ActionFeedbackType feedbackType = ActionFeedbackType.NORMAL;

        if (argsList.contains(null) || argsList.contains("")) {
            argsList.remove("");
            feedbackType = ActionFeedbackType.GENERIC;
        } else if (argsList.size() < maxVariables) {
            feedbackType = ActionFeedbackType.SELF;
        }


        this.printFeedback(feedbackType, argsList.toArray());
    }

    public static final ActionFeedback HURT = new ActionFeedback(4)
            .addFeedbackOption(ActionFeedbackType.NORMAL, "%1$s was hit by %2$s for %3$s %4$s damage! %n")
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
