package net.vami.game.interactables.entities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.interactions.patrons.Patron;
import net.vami.game.Game;
import net.vami.util.HexUtil;
import org.fusesource.jansi.AnsiConsole;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Player extends Entity implements Serializable {

    private Patron patron;

    public Player(String name, Attributes attributes) {
        super(name, attributes);
        addAvailableAction(Action.SAVE);
        addAvailableAction(Action.USE);

        addReceivableAction(Action.SAVE);

//        attributes.levelAttribute = patron.level();
//        attributes.baseDamageAttribute = patron.baseDamage();
//        attributes.armorAttribute = patron.armor();
//        attributes.abilityAttribute = patron.ability();
//        attributes.damageTypeAttribute = patron.damageType();
//        attributes.maxHealthAttribute = patron.maxHealth();
//        this.heal(this, getMaxHealth());
    }

    @Override
    public boolean receiveSave(Interactable source) {
        savePlayer(this);
        saveInteractables(this);
        return super.receiveSave(source);
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public static void savePlayer(Player player) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String saveFilePath = Game.playerSavePathFormat.replace("%", HexUtil.toHex(player.getName()));
        File saveFile = new File(saveFilePath);
        try (FileWriter saveWriter = new FileWriter(saveFilePath)) {
            saveFile.getParentFile().mkdirs();
            gson.toJson(player, saveWriter);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Player loadPlayer(String playerName) {
        String saveFilePath = Game.playerSavePathFormat.replace("%", HexUtil.toHex(playerName));
        File saveFile = new File(saveFilePath);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Player loadedPlayer = null;


        if (saveFile.exists()) {
            FileReader reader = null;
            try {
                reader = new FileReader(saveFile);
                loadedPlayer = gson.fromJson(reader, Player.class);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            saveFile.getParentFile().mkdirs();
        }

        return loadedPlayer;
    }

    public static Player createPlayer() {
        AnsiConsole.out.println("Enter your name, traveler:");
        Scanner playerNameScanner = new Scanner(System.in);
        String playerName = playerNameScanner.nextLine();
        playerName = playerName.substring(0, 1).toUpperCase() + playerName.substring(1);
        int index = 0;
        String response = "";
        while (playerName.isEmpty() || (playerName.length() >= 20)) {
            switch (index) {
                case 3 -> response = "No more, then...%n";
                case 2 -> response = "Enough of this. Name another!%n";
                case 1 -> response = "That mark will not be accepted. Try someone else.%n";
                case 0 -> response = "The gods deny that mark on the world.%nPerhaps another could continue on their journey?%n";
            }
            AnsiConsole.out.printf(response);
            if (index == 3) {
                return null;
            }
            playerName = playerNameScanner.nextLine();
            index++;
        }

        Player createdPlayer = loadPlayer(playerName);

        if (createdPlayer == null) {
            createdPlayer = new Player(playerName, new Attributes().level(2));
        }
        else {
            Interactable.addToMap(createdPlayer);
        }

        Interactable.loadInteractables(createdPlayer.getName());

        return createdPlayer;
    }

}
