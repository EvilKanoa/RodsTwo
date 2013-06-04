package ca.kanoa.RodsTwo;

import ca.kanoa.RodsTwo.Helpers.CommandExecu;
import ca.kanoa.RodsTwo.Helpers.EventListener;
import ca.kanoa.RodsTwo.Helpers.Utils;
import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.Rods.Health;
import ca.kanoa.RodsTwo.Rods.Lightning;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class RodsTwo extends JavaPlugin implements Listener{

    public Set<Rod> rods = new HashSet<Rod>();
    public Map<String, Long> cooldowns = new HashMap<String, Long>();
    PluginDescriptionFile pdf;
    public Logger logger;
    public FileConfiguration rodConfig;
    public Utils utils = new Utils(this);
    public static Plugin plugin;

    public void onEnable(){
        pdf = getDescription();
        logger = getLogger();
        plugin = this;
        getCommand("lightningrod").setExecutor(new CommandExecu(this));
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        try {
            addRods();
        } catch (Exception e) {
            e.printStackTrace();
        }

        rodConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "rods.yml"));
        utils.setRodVars();
        utils.addRecipes();

        utils.initializeRods();

        logger.info("v" + pdf.getVersion() + "enabled");
    }

    public Set<Rod> getRods(){
        return rods;
    }

    public void addRods() throws Exception{
        //TODO: Make more rods!!!
        rods.add(new Lightning(this));
        rods.add(new Health(this));
        //TODO: Make more rods!!!
        utils.makeConfig(false);
    }
}