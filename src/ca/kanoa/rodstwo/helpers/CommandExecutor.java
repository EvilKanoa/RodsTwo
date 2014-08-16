package ca.kanoa.rodstwo.helpers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.Store;
import ca.kanoa.rodstwo.rods.Rod;

import java.io.File;
import java.util.Arrays;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    private RodsTwo plugin;

    public CommandExecutor(){
        this.plugin = RodsTwo.getInstance();
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isPlayer = sender instanceof Player;
        Player player = isPlayer ? (Player) sender : null;

        if(args.length > 0 && args[0].equalsIgnoreCase("spawn")){
        	if (!sender.hasPermission("lr.spawn")) {
        		sender.sendMessage(ChatColor.RED + "You don't have permission!");
        		return false;
        	}
        	
            if(args.length == 2 || args.length == 3){
                if(isPlayer){
                    Rod rod = null;
                    for(Rod r : plugin.getRods())
                        if(r.getName().equalsIgnoreCase(args[1].replace('_', ' ')))
                            rod = r;
                    if(rod == null){
                        player.sendMessage("Couldn't find a rod with that name.");
                        return true;
                    }
                    else{
                        int x;
                        try {
                            x = Integer.parseInt(args[2]);
                        } catch(Exception e) {
                           x = 1;
                        }
                        ItemStack rodStack = new ItemStack(Material.getMaterial(rod.getItemID()), x);
                        ItemMeta rodMeta = rodStack.getItemMeta();
                        rodMeta.setLore(Arrays.asList(new String[]{rod.getName()}));
                        rodMeta.setDisplayName(rod.getName() + " Rod");
                        rodStack.setItemMeta(rodMeta);
                        player.getInventory().addItem(rodStack);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Spawned a(n) " + rodStack.getItemMeta().getDisplayName());
                        return true;
                    }
                }
                else{
                    sender.sendMessage("Player only command.");
                    return true;
                }
            }
            else if (args.length == 1){
            	if (!isPlayer) {
                    sender.sendMessage("Player only command.");
                    return true;
            	}
            	Store store = new Store((Player)sender);
            	sender.sendMessage(Store.storeMsg("Opening the store..."));
            	store.show();
            	return true;
            }
            else {
                sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                sender.sendMessage("" + ChatColor.AQUA + ChatColor.ITALIC + "/lr spawn [rod name] [amount]");
                return true;
            }
        }

        else if(args.length > 0 && args[0].equalsIgnoreCase("list")){
        	if (!sender.hasPermission("lr.list")) {
        		sender.sendMessage(ChatColor.RED + "You don't have permission!");
        		return false;
        	}
        	
            if(args.length == 1){
                sender.sendMessage("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Listing all loaded rods");
                for(Rod rod : plugin.getRods())
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Name: " + ChatColor.RED + rod.getName() + ChatColor.LIGHT_PURPLE + ", ItemID: " + ChatColor.GREEN + rod.getItemID());
            }
            else
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
            return true;
        }

        else if(args.length > 0 && args[0].equalsIgnoreCase("reload")){
        	if (!sender.hasPermission("lr.reload")) {
        		sender.sendMessage(ChatColor.RED + "You don't have permission!");
        		return false;
        	}
        	
            if(args.length == 1){
                sender.sendMessage(ChatColor.AQUA + "Reloading configuration...");
                plugin.rodConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "rods.yml"));
                Utils.loadRodOptions();
                plugin.getServer().resetRecipes();
                Utils.addRecipes();
                sender.sendMessage(ChatColor.AQUA + "Reload complete!");
            }
            else
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
            return true;
        }

        else if(args.length > 0 && args[0].equalsIgnoreCase("overwrite")){
        	if (!sender.hasPermission("lr.overwrite")) {
        		sender.sendMessage(ChatColor.RED + "You don't have permission!");
        		return false;
        	}
        	
            if(args.length == 1){
                sender.sendMessage(ChatColor.AQUA + "Overwriting configuration...");
                Utils.makeConfig(true);
                plugin.rodConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "rods.yml"));
                Utils.loadRodOptions();
                plugin.getServer().resetRecipes();
                Utils.addRecipes();
                sender.sendMessage(ChatColor.AQUA + "Overwrite complete!");
            }
            else
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
            return true;
        }
        
        else if(args.length > 0 && args[0].equalsIgnoreCase("version")) {
        	if (sender.hasPermission("lr.version"))
        		sender.sendMessage(ChatColor.DARK_GREEN + "LightningRods 2 version " + plugin.getVersion().toString() + ", made by: 12323op.");
        	else
        		sender.sendMessage(ChatColor.RED + "You don't have permission!");
    		return true;
        }

        return false;
    }
}
