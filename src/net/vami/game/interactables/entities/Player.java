package net.vami.game.interactables.entities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.ai.Brain;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.interactions.patrons.Patron;
import net.vami.game.world.Game;
import net.vami.game.world.Node;
import net.vami.util.HexUtil;

import java.io.*;
import java.util.Scanner;

public class Player extends Entity implements Serializable {

    private Patron patron;

    public Player(String name, Attributes attributes) {
        super(name, attributes);
        addAvailableAction(Action.SAVE);
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
    public Brain getBrain() {
        return null;
    }

    @Override
    public boolean receiveSave(Interactable source) {
        Entity entitySource = (Entity) source;
        if (entitySource.hasStatus()) {
            System.out.printf("%s cannot %s while afflicted... %n", entitySource.getName(), Action.SAVE.getSynonyms().stream().findAny());
            return false;
        }
        savePlayer(this);
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
        try (FileWriter saveWriter = new FileWriter(saveFilePath)) {
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

                if (!loadedPlayer.getName().equals(playerName)) {
                    loadedPlayer = null;
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return loadedPlayer;
    }

    public static Player createPlayer() {
        System.out.println("Enter your name, traveler:");
        Scanner playerNameScanner = new Scanner(System.in);
        String playerName = playerNameScanner.nextLine();

        Player createdPlayer = loadPlayer(playerName);

        if (createdPlayer == null) {
            createdPlayer = new Player(playerName, new Attributes());
        }

        if (Node.getNodeFromPosition(createdPlayer.getPos()) != null) {
            Node.getNodeFromPosition(createdPlayer.getPos()).addInteractable(createdPlayer);
        }

        return createdPlayer;
    }

}
