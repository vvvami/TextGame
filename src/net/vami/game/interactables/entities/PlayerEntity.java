package net.vami.game.interactables.entities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.ai.Brain;
import net.vami.game.interactables.ai.tasks.*;
import net.vami.game.interactables.interactions.abilities.HypnosisAbility;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.interactions.patrons.Patron;
import net.vami.game.Game;
import net.vami.game.interactables.items.custom.ExplorersMapItem;
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
        TextUtil.display("The Goddess still loves you.%nGood luck, %s.%n", Color.gray, this.getDisplayName());
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
            FileReader reader = null;
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
        playerName = playerName.substring(0, 1).toUpperCase() + playerName.substring(1);

        if (playerName.isEmpty() || (playerName.length() >= 20) || !playerName.matches("[a-zA-Z]+")) {
            LogUtil.Log(LoggerType.ERROR, "Name is invalid!");
            return null;
        }

        PlayerEntity createdPlayer = loadPlayer(playerName);

        if (createdPlayer == null) {
            createdPlayer = new PlayerEntity(playerName, new Attributes()
                    .level(5)
                    .ability(HypnosisAbility.get));
            createdPlayer.addInventoryItem(new ExplorersMapItem("Map"));
            TextUtil.display("Your adventure begins. %n");
        }

        Interactable.loadInteractables(createdPlayer.getName());

        return createdPlayer;
    }

}
