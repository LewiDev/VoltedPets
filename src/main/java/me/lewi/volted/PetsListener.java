package me.lewi.volted;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetsListener implements Listener {

    private Pets plugin;

    public PetsListener(Pets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        try {
            PreparedStatement statement = plugin.getDb().getConnection().prepareStatement("SELECT WOLF, MINER, EAGLE, ACTIVE_PET FROM player_pets WHERE UUID = ?");
            statement.setString(1, e.getPlayer().getUniqueId().toString());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                PreparedStatement statement1 = plugin.getDb().getConnection().prepareStatement("INSERT INTO player_pets (UUID, WOLF, MINER, EAGLE, ACTIVE_PET) VALUES (" +
                        "'" + e.getPlayer().getUniqueId().toString() + "'," +
                        0 + "," +
                        0 + "," +
                        0 + "," +
                        0 +
                        ");");
                statement1.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getPm().loadPlayerData(e.getPlayer());
        plugin.getPetManager().despawnPet(e.getPlayer(), plugin.getPetManager().getPet(e.getPlayer()));
        plugin.getPm().setActivePet(e.getPlayer(),0);
        plugin.currentPets.get(e.getPlayer().getUniqueId()).remove();
        plugin.currentPets.remove(e.getPlayer().getUniqueId());

    }
}
