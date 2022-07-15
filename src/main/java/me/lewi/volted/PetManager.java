package me.lewi.volted;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PetManager {

    private final Pets plugin;
    private int petint;

    private BukkitTask petmove;

    public PetManager(Pets plugin) {
        this.plugin = plugin;
    }

    public PetType getPet(OfflinePlayer p) {
        plugin.getPm().loadPlayerData(p);

        if (plugin.getPm().getActivePet(p) == 1) {
            return PetType.wolf;
        } else if (plugin.getPm().getActivePet(p) == 2) {
            return PetType.miner;
        } else if (plugin.getPm().getActivePet(p) == 3) {
            return PetType.eagle;
        } else {
            return PetType.none;
        }

    }

    public void setPet(OfflinePlayer p, PetType pet) {
        plugin.getPm().loadPlayerData(p);
        if (plugin.getPm().getActivePet(p) == 0) {
            if (pet.equals(PetType.wolf)) {
                petint = 1;
            } else if (pet.equals(PetType.miner)) {
                petint = 2;
            } else if (pet.equals(PetType.eagle)) {
                petint = 3;
            } else {
                petint = 0;
            }
            plugin.getPm().setActivePet(p, petint);
        } else {
            if(p.isOnline()) {
                p.getPlayer().sendMessage(ChatColor.RED + "You must disable your other pet first!");
            }
        }
    }

    public void givePet(OfflinePlayer p, PetType pet) {
        plugin.getPm().loadPlayerData(p);
        if(pet.equals(PetType.wolf)) {
            plugin.getPm().setWolf(p,1);
        } else if (pet.equals(PetType.miner)) {
            plugin.getPm().setMiner(p,1);
        } else if (pet.equals(PetType.eagle)) {
            plugin.getPm().setEagle(p,1);
        }
    }

    public void spawnPet(Player p, ItemStack pet_head) {
        Location loc = p.getLocation().add(0, 2, 0);
        ArmorStand petas = (ArmorStand) p.getLocation().getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        petas.setVisible(false);
        petas.setGravity(false);
        petas.setInvulnerable(true);
        petas.setHelmet(pet_head);
        plugin.currentPets.put(p.getUniqueId(), petas);

        petmove = new BukkitRunnable() {
            @Override
            public void run() {
                Location loc1 = p.getLocation().add(0, 1.5, 0);
                petas.teleport(loc1);
            }
        }.runTaskTimer(plugin, 0, 2);

    }

    public void despawnPet(Player p,  PetType pet) {
        if(pet.equals(PetType.wolf)) {
            p.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
            plugin.getPm().setActivePet(p, 0);
        } else if (pet.equals(PetType.miner)) {
            plugin.miner.get(p.getUniqueId()).cancel();
            plugin.getPm().setActivePet(p, 0);
        } else if (pet.equals(PetType.eagle)) {
            p.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            plugin.getPm().setActivePet(p, 0);
        }
        plugin.currentPets.get(p.getUniqueId()).remove();
        plugin.currentPets.remove(p.getUniqueId());
    }

}
