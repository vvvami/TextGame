package net.vami.game.interactable.entity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.vami.game.display.Display;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.ai.EntityMood;
import net.vami.game.interactable.interaction.abilities.Abilities;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.interaction.patrons.Patron;
import net.vami.game.Game;
import net.vami.game.interactable.interaction.patrons.Patrons;
import net.vami.game.interactable.item.Items;
import net.vami.util.*;

import java.awt.*;
import java.io.*;

public class PlayerEntity extends Entity {
    private Patron patron;

    public PlayerEntity(String name, Attributes attributes) {
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
    public void remove() {
        this.removeAllItems();
        super.remove();
    }

    @Override
    public void initializeBrain() {

    }

    @Override
    public boolean receiveSave(Interactable source) {
        savePlayer(this);
        saveInteractables(this);
        Display.showText("The Goddess still loves you.%nGood luck, %s.%n", Color.gray, this);
        return true;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public static void savePlayer(PlayerEntity player) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String saveFilePath = Game.playerSavePathFormat.replace("%", HexUtil.toHex(player.getName()));
        File saveFile = new File(saveFilePath);
        saveFile.getParentFile().mkdirs();
        try (FileWriter saveWriter = new FileWriter(saveFilePath)) {
            gson.toJson(player, saveWriter);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PlayerEntity loadPlayer(String playerName) {
        String saveFilePath = Game.playerSavePathFormat.replace("%", HexUtil.toHex(playerName));
        File saveFile = new File(saveFilePath);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        PlayerEntity loadedPlayer = null;


        if (saveFile.exists()) {
            FileReader reader;
            try {
                reader = new FileReader(saveFile);
                loadedPlayer = gson.fromJson(reader, PlayerEntity.class);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return loadedPlayer;
    }

    public static PlayerEntity createPlayer(String name) {
        String playerName = name;
        playerName = capitalize(playerName);

        if (isInvalidName(playerName)) {
            LogUtil.log(LoggerType.ERROR, "Name is invalid!");
            return null;
        }

        PlayerEntity createdPlayer = loadPlayer(playerName);

        if (createdPlayer == null) {

            Patron randPatron = Patrons.ARTHUUROS;
            createdPlayer = new PlayerEntity(playerName, new Attributes()
                    .level(randPatron.level())
                    .armor(randPatron.armor())
                    .ability(randPatron.ability())
                    .maxHealth(randPatron.maxHealth())
                    .baseDamage(randPatron.baseDamage())
                    .damageType(randPatron.damageType()));

            randPatron.init(createdPlayer);
            createdPlayer.setPatron(randPatron);

            createdPlayer.addInventoryItem(Items.EXPLORERS_MAP.create());
            createdPlayer.addEquippedItem(Items.CENTRIFUGE.create());

            Display.print("Your adventure begins. %n");
        }

        Interactable.loadInteractables(createdPlayer.getName());

        return createdPlayer;
    }

    private static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private static boolean isInvalidName(String playerName) {
        return (playerName.isEmpty() || (playerName.length() >= 20) || !playerName.matches("[a-zA-Z]+"));
    }

    @Override
    public void createInteractableRating(Interactable ia) {
        float rating = 0f;
        if (ia instanceof PlayerEntity) {
            rating = EntityMood.FRIENDLY.get();
        }
        createMoodRating(ia, rating);
    }

    public void applyPatron() {
        if (patron == null) return;

        this.getAttributes()
                .level(patron.level())
                .armor(patron.armor())
                .baseDamage(patron.baseDamage())
                .maxHealth(patron.maxHealth())
                .ability(patron.ability())
                .damageType(patron.damageType());
    }
}
