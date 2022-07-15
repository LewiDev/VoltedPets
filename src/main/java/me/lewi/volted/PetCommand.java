package me.lewi.volted;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PetCommand implements CommandExecutor {

    private final Pets plugin;

    public PetCommand(Pets plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only a player can use this command!");
        } else {
            Player p = (Player) sender;
            if(args.length == 0) {
                plugin.getPetsGUI().openPetGUI(p);
            } else if (args.length == 3) {
                if(args[0].equalsIgnoreCase("give")) {
                    if(p.hasPermission("pets.give")) {
                        OfflinePlayer t = Bukkit.getPlayer(args[1]);
                        if(t != null) {
                            if (args[2].equalsIgnoreCase("wolf")) {
                                plugin.getPetManager().givePet(t, PetType.wolf);
                                p.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.WHITE + args[1] + ChatColor.GREEN + "a wolf pet!");
                            } else if (args[2].equalsIgnoreCase("miner")) {
                                plugin.getPetManager().givePet(t, PetType.miner);
                                p.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.WHITE + args[1] + ChatColor.GREEN + "a miner pet!");
                            } else if (args[2].equalsIgnoreCase("eagle")) {
                                plugin.getPetManager().givePet(t, PetType.eagle);
                                p.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.WHITE + args[1] + ChatColor.GREEN + "a eagle pet!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Invalid Pet: wolf, miner, eagle");
                            }
                    } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid Player: please provide a real player");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid Usage: /pets give <player> <pet>");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Invalid Usage: /pets give <player> <pet> OR /pets");
            }
        }



        return false;
    }
}
