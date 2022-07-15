package me.lewi.volted;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PetsGUI {

    private Pets plugin;

    public PetsGUI(Pets plugin) {
        this.plugin = plugin;
    }

    Gui gui = Gui.gui()
            .title(Component.text(ChatColor.translateAlternateColorCodes('&', "&0&lPets")))
            .rows(5)
            .create();

    public void openPetGUI(Player p) {
        plugin.getPm().loadPlayerData(p);

        OfflinePlayer dog = Bukkit.getOfflinePlayer("Dog");
        ItemStack wolf = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta wolfItemMeta = (SkullMeta) wolf.getItemMeta();
        wolfItemMeta.setOwningPlayer(dog);
        wolfItemMeta.setDisplayName(ChatColor.AQUA + ChatColor.BOLD.toString() + "Wolf Pet");
        ArrayList<String> wolflore = new ArrayList<String>();
        wolflore.add("");
        wolflore.add(ChatColor.GRAY + "whilst having this pet enabled");
        wolflore.add(ChatColor.GRAY + "You will have a permanent HASTE effect");
        wolflore.add("");
        wolflore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + ChatColor.BOLD + "Legendary");
        wolflore.add("");
        if(plugin.getPm().getWolf(p) == 1) {
            wolflore.add(ChatColor.GREEN + ChatColor.BOLD.toString() + "UNLOCKED");
        } else {
            wolflore.add(ChatColor.RED + ChatColor.BOLD.toString() + "LOCKED");
        }
        wolfItemMeta.setLore(wolflore);
        wolf.setItemMeta(wolfItemMeta);

        OfflinePlayer minerh = Bukkit.getOfflinePlayer("FireFlakess");
        ItemStack miner = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta minerItemMeta = (SkullMeta) miner.getItemMeta();
        minerItemMeta.setOwningPlayer(minerh);
        minerItemMeta.setDisplayName(ChatColor.AQUA + ChatColor.BOLD.toString() + "Miner Pet");
        ArrayList<String> minerlore = new ArrayList<String>();
        minerlore.add("");
        minerlore.add(ChatColor.GRAY + "whilst having this pet enabled");
        minerlore.add(ChatColor.GRAY + "random loot will appear into your inventory");
        minerlore.add("");
        minerlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + ChatColor.BOLD + "Legendary");
        minerlore.add("");
        minerlore.add(ChatColor.GRAY + "make sure your inventory isn't too full!!");
        minerlore.add("");
        if (plugin.getPm().getMiner(p) == 1) {
            minerlore.add(ChatColor.GREEN + ChatColor.BOLD.toString() + "UNLOCKED");
        } else {
            minerlore.add(ChatColor.RED + ChatColor.BOLD.toString() + "LOCKED");
        }
        minerItemMeta.setLore(minerlore);
        miner.setItemMeta(minerItemMeta);

        OfflinePlayer eagleh = Bukkit.getOfflinePlayer("MakeMeDarkHawk");
        ItemStack eagle = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta eagleItemMeta = (SkullMeta) eagle.getItemMeta();
        eagleItemMeta.setOwningPlayer(eagleh);
        eagleItemMeta.setDisplayName(ChatColor.AQUA + ChatColor.BOLD.toString() + "Eagle Pet");
        ArrayList<String> eaglelore = new ArrayList<String>();
        eaglelore.add("");
        eaglelore.add(ChatColor.GRAY + "whilst having this pet enabled");
        eaglelore.add(ChatColor.GRAY + "You will have a permanent SPEED effect!");
        eaglelore.add("");
        eaglelore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + ChatColor.BOLD + "Legendary");
        eaglelore.add("");
        if (plugin.getPm().getEagle(p) == 1) {
            eaglelore.add(ChatColor.GREEN + ChatColor.BOLD.toString() + "UNLOCKED");
        } else {
            eaglelore.add(ChatColor.RED + ChatColor.BOLD.toString() + "LOCKED");
        }
        eagleItemMeta.setLore(eaglelore);
        eagle.setItemMeta(eagleItemMeta);


        ItemStack currentPet = new ItemStack(Material.OAK_SIGN);
        ItemMeta currentPetMeta = currentPet.getItemMeta();
        currentPetMeta.setDisplayName(ChatColor.AQUA + "Current Pet");
        ArrayList<String> currentPetLore = new ArrayList<>();
        currentPetLore.add("");
        if(plugin.getPetManager().getPet(p).equals(PetType.wolf)) {
            currentPetLore.add(ChatColor.GRAY + "Current Pet: " + ChatColor.RED + ChatColor.BOLD + "Wolf");
        } else if(plugin.getPetManager().getPet(p).equals(PetType.miner)) {
            currentPetLore.add(ChatColor.GRAY + "Current Pet: " + ChatColor.RED + ChatColor.BOLD + "Miner");
        } else if(plugin.getPetManager().getPet(p).equals(PetType.eagle)) {
            currentPetLore.add(ChatColor.GRAY + "Current Pet: " + ChatColor.RED + ChatColor.BOLD + "Eagle");
        } else {
            currentPetLore.add(ChatColor.RED + "You do not have a pet activated");
        }
        currentPetLore.add("");
        currentPetMeta.setLore(currentPetLore);
        currentPet.setItemMeta(currentPetMeta);


        ItemStack removePet = new ItemStack(Material.BARRIER);
        ItemMeta removePetMeta = removePet.getItemMeta();
        removePetMeta.setDisplayName(ChatColor.DARK_RED + "Despawn Pet");
        ArrayList<String> removePetLore = new ArrayList<>();
        removePetLore.add("");
        removePetLore.add(ChatColor.GRAY + "Despawns the current pet you are using!");
        removePetLore.add("");
        removePetMeta.setLore(removePetLore);
        removePet.setItemMeta(removePetMeta);

        GuiItem wolfItem = new GuiItem(wolf);
        wolfItem.setAction(e ->{
            e.setCancelled(true);
            if(plugin.getPm().hasWolf(p) && !plugin.getPm().isActive(p)) {
                gui.close(p);
                plugin.getPetManager().setPet(p, PetType.wolf);
                plugin.getPetManager().spawnPet(p, wolf);
                e.getWhoClicked().addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(Integer.MAX_VALUE, 1));
                p.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Activated your Wolf Pet!");
            } else if(!plugin.getPm().hasWolf(p)) {
                gui.close(p);
                p.sendMessage(ChatColor.RED + "You have not unlocked the wolf pet yet!");
            } else if(!plugin.getPetManager().getPet(p).equals(PetType.wolf)) {
                gui.close(p);
                p.sendMessage(ChatColor.RED + "You have to disable one of your pets first!");
            }
        });

        GuiItem minerItem = new GuiItem(miner);
        minerItem.setAction(e ->{
            e.setCancelled(true);
            if(plugin.getPm().hasMiner(p) && !plugin.getPm().isActive(p)) {
                gui.close(p);
                plugin.getPetManager().setPet(p, PetType.miner);
                plugin.getPetManager().spawnPet(p, miner);
                BukkitTask minertask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        ItemStack item = new ItemStack(Material.matchMaterial(plugin.getConfig().getStringList("minerrewards").get(ThreadLocalRandom.current().nextInt(0, plugin.getConfig().getStringList("minerrewards").size() - 1))));
                        p.getInventory().addItem(item);
                    }
                }.runTaskTimer(plugin, 0, (60 * 20));
                plugin.miner.put(e.getWhoClicked().getUniqueId(), minertask);
                p.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Activated your Miner Pet!");
            } else if(!plugin.getPm().hasMiner(p)) {
                gui.close(p);
                p.sendMessage(ChatColor.RED + "You have not unlocked the miner pet yet!");
            } else if(!plugin.getPetManager().getPet(p).equals(PetType.miner)) {
                gui.close(p);
                p.sendMessage(ChatColor.RED + "You have to disable one of your pets first!");
            }
        });

        GuiItem eagleItem = new GuiItem(eagle);
        eagleItem.setAction(e ->{
            e.setCancelled(true);
            if(plugin.getPm().hasEagle(p) && !plugin.getPm().isActive(p)) {
                gui.close(p);
                plugin.getPetManager().setPet(p, PetType.eagle);
                plugin.getPetManager().spawnPet(p, eagle);
                e.getWhoClicked().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 1));
                p.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Activated your Eagle Pet!");
            } else if(!plugin.getPm().hasEagle(p)) {
                gui.close(p);
                p.sendMessage(ChatColor.RED + "You have not unlocked the eagle pet yet!");
            } else if(!plugin.getPetManager().getPet(p).equals(PetType.eagle)) {
                gui.close(p);
                p.sendMessage(ChatColor.RED + "You have to disable one of your pets first!");
            }
        });

        GuiItem current = new GuiItem(currentPet);
        current.setAction(e ->{
            e.setCancelled(true);
        });

        GuiItem remove = new GuiItem(removePet);
        remove.setAction(e -> {
            e.setCancelled(true);
            if (plugin.getPm().isActive(p)) {
                plugin.getPetManager().despawnPet(p, plugin.getPetManager().getPet(p));
                gui.close(p);
                p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "Deactivated your pet!");
            } else {
                gui.close(p);
                p.sendMessage(ChatColor.RED + "You do not have a pet active!");
            }

        });

        GuiItem background = new GuiItem(Material.BLACK_STAINED_GLASS_PANE);
        background.setAction(e -> {
            e.setCancelled(true);
        });

        gui.setItem(11, wolfItem);
        gui.setItem(13, minerItem);
        gui.setItem(15, eagleItem);
        gui.setItem(30, current);
        gui.setItem(32, remove);

        gui.setItem(0, background);
        gui.setItem(1, background);
        gui.setItem(2, background);
        gui.setItem(3, background);
        gui.setItem(4, background);
        gui.setItem(5, background);
        gui.setItem(6, background);
        gui.setItem(7, background);
        gui.setItem(8, background);
        gui.setItem(9, background);
        gui.setItem(10, background);
        gui.setItem(12, background);
        gui.setItem(14, background);
        gui.setItem(16, background);
        gui.setItem(17, background);
        gui.setItem(19, background);
        gui.setItem(18, background);
        gui.setItem(20, background);
        gui.setItem(21, background);
        gui.setItem(22, background);
        gui.setItem(23, background);
        gui.setItem(24, background);
        gui.setItem(25, background);
        gui.setItem(26, background);
        gui.setItem(27, background);
        gui.setItem(28, background);
        gui.setItem(29, background);
        gui.setItem(31, background);
        gui.setItem(33, background);
        gui.setItem(34, background);
        gui.setItem(35, background);
        gui.setItem(36, background);
        gui.setItem(37, background);
        gui.setItem(38, background);
        gui.setItem(39, background);
        gui.setItem(40, background);
        gui.setItem(41, background);
        gui.setItem(42, background);
        gui.setItem(43, background);
        gui.setItem(44, background);


        gui.open(p);


    }




}
