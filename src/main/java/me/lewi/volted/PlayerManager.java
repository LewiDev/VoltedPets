package me.lewi.volted;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerManager {

    private final Pets plugin;

    PlayerManager(Pets plugin) {
        this.plugin = plugin;
    }

    private int wolf;
    private int miner;
    private int eagle;

    private int active_pet;

    public void loadPlayerData(OfflinePlayer p) {
        UUID uuid = p.getUniqueId();
        try {
            PreparedStatement statement = plugin.getDb().getConnection().prepareStatement("SELECT WOLF, MINER, EAGLE, ACTIVE_PET FROM player_pets WHERE UUID = ?");
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                wolf = rs.getInt("WOLF");
                miner = rs.getInt("MINER");
                eagle = rs.getInt("EAGLE");
                active_pet = rs.getInt("ACTIVE_PET");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getWolf(OfflinePlayer p) {
        try {
            PreparedStatement statement = plugin.getDb().getConnection().prepareStatement("SELECT WOLF FROM player_pets WHERE UUID = ?");
            statement.setString(1, p.getUniqueId().toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("WOLF");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setWolf(OfflinePlayer p, Integer i) {
        wolf = i;
        try {
            PreparedStatement statement2 = plugin.getDb().getConnection().prepareStatement("UPDATE player_pets SET WOLF = "+ wolf +" WHERE UUID = '" + p.getUniqueId().toString() + "';");
            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMiner(OfflinePlayer p) {
        try {
            PreparedStatement statement = plugin.getDb().getConnection().prepareStatement("SELECT MINER FROM player_pets WHERE UUID = ?");
            statement.setString(1, p.getUniqueId().toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("MINER");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setMiner(OfflinePlayer p, Integer i) {
        miner = i;
        try {
            PreparedStatement statement2 = plugin.getDb().getConnection().prepareStatement("UPDATE player_pets SET MINER = "+ miner +" WHERE UUID = '" + p.getUniqueId().toString() + "';");
            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getEagle(OfflinePlayer p) {
        try {
            PreparedStatement statement = plugin.getDb().getConnection().prepareStatement("SELECT EAGLE FROM player_pets WHERE UUID = ?");
            statement.setString(1, p.getUniqueId().toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("EAGLE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setEagle(OfflinePlayer p, Integer i) {
        eagle = i;
        try {
            PreparedStatement statement2 = plugin.getDb().getConnection().prepareStatement("UPDATE player_pets SET EAGLE = "+ eagle +" WHERE UUID = '" + p.getUniqueId().toString() + "';");
            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getActivePet(OfflinePlayer p) {
        try {
            PreparedStatement statement = plugin.getDb().getConnection().prepareStatement("SELECT ACTIVE_PET FROM player_pets WHERE UUID = ?");
            statement.setString(1, p.getUniqueId().toString());
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("ACTIVE_PET");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setActivePet(OfflinePlayer p, Integer i) {
        active_pet = i;
        try {
            PreparedStatement statement2 = plugin.getDb().getConnection().prepareStatement("UPDATE player_pets SET ACTIVE_PET = "+ active_pet +" WHERE UUID = '" + p.getUniqueId().toString() + "';");
            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasWolf(OfflinePlayer p) {
        if(getWolf(p) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasMiner(OfflinePlayer p) {
        if(getMiner(p) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEagle(OfflinePlayer p) {
        if(getEagle(p) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isActive(OfflinePlayer p) {
        if(getActivePet(p) != 0) {
            return true;
        } else {
            return false;
        }
    }


}
