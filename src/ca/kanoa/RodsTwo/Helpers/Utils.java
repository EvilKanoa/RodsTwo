package ca.kanoa.RodsTwo.Helpers;

import ca.kanoa.RodsTwo.Objects.ConfigRecipe;
import ca.kanoa.RodsTwo.Objects.InvalidRecipeException;
import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.RodsTwo;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;
import java.io.IOException;

public class Utils {

    RodsTwo plugin;
    ConfigRecipe configRecipe;

    public Utils(RodsTwo plugin){
        this.plugin = plugin;
        configRecipe = new ConfigRecipe();
    }

    public void makeConfig(boolean overwrite) {
        File configFile = new File(plugin.getDataFolder(), "rods.yml");
        if(!configFile.exists()){
            plugin.saveResource("rods.yml", true);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        for(Rod rod : plugin.rods){
            if(config.get(rod.getPath("enabled")) == null || overwrite)
                config.set(rod.getPath("enabled"), true);

            if(config.get(rod.getPath("cost")) == null || overwrite)
                config.set(rod.getPath("cost"), overwrite ? rod.getDefaultCost() : rod.getCost());

            if(config.get(rod.getPath("cooldown")) == null || overwrite)
                config.set(rod.getPath("cooldown"), overwrite ? rod.getDefaultCooldown() : rod.getCooldown());

            configRecipe.saveDefaultRecipe(config, rod, overwrite);

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

    public void setRodVars(){
        for(Rod rod : plugin.rods){
            try{
                rod.setCost(plugin.rodConfig.getInt(rod.getPath("cost")));
                rod.setCooldown(plugin.rodConfig.getLong(rod.getPath("cooldown")));
            } catch (Exception e){
                plugin.logger.warning("Invalid config formatting for rod: " + rod.getName() + ".");
            }
        }
    }

    public void addRecipes(){
        for(Rod rod : plugin.rods){
            try {
                ShapedRecipe recipe = configRecipe.loadRecipeFromConfig(plugin.rodConfig, rod);
                plugin.getServer().addRecipe(recipe);
            } catch (InvalidRecipeException e) {
                e.printStackTrace();
                plugin.logger.warning("Error while loading recipe for rod: " + rod.getName() + "!");
            } catch(NullPointerException e){e.printStackTrace();}
        }
    }

    public boolean isCooldownOver(String player){
        try{
            return System.currentTimeMillis() > plugin.cooldowns.get(player);
        } catch(Exception e){
            return true;
        }
    }

    public void initializeRods(){
        for(Rod rod : plugin.rods){
            if(!rod.enable(plugin.getServer()))
                plugin.rods.remove(rod);
        }
    }
}
