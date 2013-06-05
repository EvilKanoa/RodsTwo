package ca.kanoa.RodsTwo.Helpers;

import ca.kanoa.RodsTwo.Objects.ConfigRecipe;
import ca.kanoa.RodsTwo.Objects.InvalidRecipeException;
import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.RodsTwo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;
import java.io.IOException;

public class Utils {

    public static void makeConfig(boolean overwrite) {
        File configFile = new File(RodsTwo.plugin.getDataFolder(), "rods.yml");
        if(!configFile.exists()){
            RodsTwo.plugin.saveResource("rods.yml", true);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        for(Rod rod : RodsTwo.rods){
            if(config.get(rod.getPath("enabled")) == null || overwrite)
                config.set(rod.getPath("enabled"), true);

            if(config.get(rod.getPath("cost")) == null || overwrite)
                config.set(rod.getPath("cost"), overwrite ? rod.getDefaultCost() : rod.getCost());

            if(config.get(rod.getPath("cooldown")) == null || overwrite)
                config.set(rod.getPath("cooldown"), overwrite ? rod.getDefaultCooldown() : rod.getCooldown());

            ConfigRecipe.saveDefaultRecipe(config, rod, overwrite);

            for(Object[] s : rod.getOptions().getAllOptionsAsArray()){
                if(config.get(rod.getPath("options." + s[0])) == null || overwrite)
                    config.set(rod.getPath("options." + s[0]), s[1]);
            }
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: load set of rods from folder plugins/lr2/rods

    public static void setRodVars(){
        for(Rod rod : RodsTwo.rods){
            try{
                rod.setCost(RodsTwo.plugin.rodConfig.getInt(rod.getPath("cost")));
                rod.setCooldown(RodsTwo.plugin.rodConfig.getLong(rod.getPath("cooldown")));
            } catch (Exception e){
                RodsTwo.plugin.logger.warning("Invalid config formatting for rod: " + rod.getName() + ".");
            }
        }
    }

    public static void addRecipes(){
        for(Rod rod : RodsTwo.rods){
            try {
                ShapedRecipe recipe = ConfigRecipe.loadRecipeFromConfig(RodsTwo.plugin.rodConfig, rod);
                RodsTwo.plugin.getServer().addRecipe(recipe);
            } catch (InvalidRecipeException e) {
                e.printStackTrace();
                RodsTwo.plugin.logger.warning("Error while loading recipe for rod: " + rod.getName() + "!");
            } catch(NullPointerException e){e.printStackTrace();}
        }
    }

    public static boolean isCooldownOver(String player){
        try{
            return System.currentTimeMillis() > RodsTwo.plugin.cooldowns.get(player);
        } catch(Exception e){
            return true;
        }
    }

    public static void initializeRods(){
        for(Rod rod : RodsTwo.rods){
            if(!rod.enable(RodsTwo.plugin.getServer()))
                RodsTwo.rods.remove(rod);
        }
    }
    
    public static void debug(String msg) {
    	Bukkit.broadcastMessage(ChatColor.YELLOW + "" + '[' + ChatColor.AQUA + "Debug" + ChatColor.YELLOW + "] " + ChatColor.RED + msg);
    }
}
