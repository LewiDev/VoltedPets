package me.lewi.volted;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public final class Pets extends JavaPlugin {

    public HashMap<UUID, BukkitTask> miner = new HashMap<>();

    public HashMap<UUID, Entity> currentPets = new HashMap<>();

    String myTableName = "CREATE TABLE IF NOT EXISTS `player_pets` (`UUID` VARCHAR(36), `WOLF` INT(11), `MINER` INT(11), `EAGLE` INT(11), `ACTIVE_PET` INT(11))";

    private Database db;
    private PlayerManager pm;
    private PetManager petManager;
    private PetsGUI petsGUI;

    @Override
    public void onEnable() {
        db = new Database(this);
        pm = new PlayerManager(this);
        petManager = new PetManager(this);
        petsGUI = new PetsGUI(this);
        this.saveDefaultConfig();

        Bukkit.getLogger().log(Level.FINE, ChatColor.GREEN + "Volted Pets V1 by LewiDEV");

        try {
            db.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (db.isConnected()) {
            try {
                Statement statement = db.getConnection().createStatement();
                statement.executeUpdate(myTableName);
                System.out.println("CREATED TABLE");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        this.getCommand("pets").setExecutor(new PetCommand(this));
        this.getServer().getPluginManager().registerEvents(new PetsListener(this), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            getPm().loadPlayerData(player);
            getPetManager().despawnPet(player, getPetManager().getPet(player));
            getPm().setActivePet(player,0);
            currentPets.get(player.getUniqueId()).remove();
            currentPets.remove(player.getUniqueId());

        });

        if(db.isConnected()) {
            db.disconnect();
        }

    }

    public Database getDb() {
        return db;
    }

    public PlayerManager getPm() {
        return pm;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    public PetsGUI getPetsGUI() {
        return petsGUI;
    }

}
