package ca.kanoa.RodsTwo.Helpers;

import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.RodsTwo;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    private RodsTwo plugin;

    public CommandExecutor(RodsTwo plugin){
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isPlayer = sender instanceof Player;
        Player player = isPlayer ? (Player) sender : null;

        if(args.length > 0 && args[0].equalsIgnoreCase("spawn")){
            if(args.length == 2 || args.length == 3){
                if(isPlayer){
                    Rod rod = null;
                    for(Rod r : plugin.getRods())
                        if(r.getName().equalsIgnoreCase(args[1]))
                            rod = r;
                    if(rod == null){
                        player.sendMessage("Couldn't find a rod with that name.");
                        return true;
                    }
                    else{
                        int x = 1;
                        try{
                            x = Integer.parseInt(args[2]);
                        } catch(Exception e){
                            //Do nothing
                        }
                        ItemStack rodStack = new ItemStack(Material.getMaterial(rod.getRodID()), x);
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
            else{
                sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                return true;
            }
        }

        else if(args.length > 0 && args[0].equalsIgnoreCase("list")){
            if(args.length == 1){
                sender.sendMessage(ChatColor.DARK_PURPLE + "Listing all loaded rods");
                for(Rod rod : plugin.getRods())
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Name: " + ChatColor.RED + rod.getName() + ChatColor.LIGHT_PURPLE + ", Cost: " + ChatColor.WHITE + rod.getCost() + ChatColor.LIGHT_PURPLE + ", ItemID: " + ChatColor.GREEN + rod.getRodID());
            }
            else
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
            return true;
        }

        else if(args.length > 0 && args[0].equalsIgnoreCase("reload")){
            if(args.length == 1){
                sender.sendMessage(ChatColor.DARK_BLUE + "Reloading configuration...");
                plugin.rodConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "rods.yml"));
                Utils.setRodVars();
                plugin.getServer().resetRecipes();
                Utils.addRecipes();
                sender.sendMessage(ChatColor.AQUA + "Reload complete!");
            }
            else
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
            return true;
        }

        else if(args.length > 0 && args[0].equalsIgnoreCase("overwrite")){
            if(args.length == 1){
                sender.sendMessage(ChatColor.DARK_BLUE + "Overwriting configuration...");
                Utils.makeConfig(true);
                plugin.rodConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "rods.yml"));
                Utils.setRodVars();
                plugin.getServer().resetRecipes();
                Utils.addRecipes();
                sender.sendMessage(ChatColor.AQUA + "Overwrite complete!");
            }
            else
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
            return true;
        }

        return false;
    }
}
