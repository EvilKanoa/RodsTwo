package ca.kanoa.RodsTwo;

import ca.kanoa.RodsTwo.Helpers.CommandExecutor;
import ca.kanoa.RodsTwo.Helpers.EventListener;
import ca.kanoa.RodsTwo.Helpers.Utils;
import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.Rods.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class RodsTwo extends JavaPlugin implements Listener{

    public static Set<Rod> rods = new HashSet<Rod>();
    public Map<String, Long> cooldowns = new HashMap<String, Long>();
    PluginDescriptionFile pdf;
    public Logger logger;
    public FileConfiguration rodConfig;
    public static RodsTwo plugin;

    public void onEnable(){
        pdf = getDescription();
        logger = getLogger();
        plugin = this;
        getCommand("lightningrod").setExecutor(new CommandExecutor(this));
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        try {
            addRods();
        } catch (Exception e) {
            e.printStackTrace();
        }

        rodConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "rods.yml"));
        Utils.setRodVars();
        Utils.addRecipes();

        Utils.initializeRods();

        logger.info(pdf.getName() + " v" + pdf.getVersion() + " enabled");
    }

    public Set<Rod> getRods(){
        return rods;
    }

    private void addRods() throws Exception {
        //TODO: Make more rods!!!
        rods.add(new Lightning(this));
        rods.add(new Health(this));
        rods.add(new Arrow(this));
        rods.add(new Companion(this));
        rods.add(new Creeper(this));
        rods.add(new Ender(this));
        rods.add(new Explosion(this));
        rods.add(new Fire(this));
        rods.add(new Hunger(this));
        rods.add(new Parkour(this));
        rods.add(new Weather(this));
        rods.add(new Flight(this));
        rods.add(new Zombie(this));
        rods.add(new Drown(this));
        //TODO: Make more rods!!!
        Utils.makeConfig(false);
    }
    
    public static void addRods(Rod[] rods) {
    	for (Rod rod : rods)
    		ca.kanoa.RodsTwo.RodsTwo.rods.add(rod);
    	Utils.makeConfig(false);
    }
    
    public static void addRods(Set<Rod> rods) {
    	addRods(rods.toArray(new Rod[0]));
    }
    
    public static void addRods(List<Rod> rods) {
    	addRods(rods.toArray(new Rod[0]));
    }
    
    public static void addRod(Rod rod) {
    	rods.add(rod);
    	Utils.makeConfig(false);
    }
}