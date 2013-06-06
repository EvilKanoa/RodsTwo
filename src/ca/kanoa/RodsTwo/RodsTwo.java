package ca.kanoa.RodsTwo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ca.kanoa.RodsTwo.Helpers.*;
import ca.kanoa.RodsTwo.Listeners.*;
import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.Rods.*;

public class RodsTwo extends JavaPlugin implements Listener{

    public static List<Rod> rods;
    public Map<String, Long> cooldowns;
    PluginDescriptionFile pdf;
    public Logger logger;
    public FileConfiguration rodConfig;
    public static RodsTwo plugin;

    public void onEnable(){
    	rods = new ArrayList<Rod>();
    	cooldowns = new HashMap<String, Long>();
        pdf = getDescription();
        logger = getLogger();
        plugin = this;
        
        getCommand("lightningrod").setExecutor(new CommandExecutor());
        Bukkit.getPluginManager().registerEvents(new CastListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
        Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
        
        if (Bukkit.getPluginManager().isPluginEnabled("Vault"))
        	VaultManager.eco = VaultManager.setupEconomy();
        else
        	getLogger().warning("Vault has not been enabled, using signs will not cost any money!");
        
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

    public List<Rod> getRods(){
        return rods;
    }

    private void addRods() throws Exception {
        //TODO: Make more rods!!!
        rods.add(new Arrow(this));
        rods.add(new Broadcast(this));
        rods.add(new Companion(this));
        rods.add(new Cake(this));
        rods.add(new Creeper(this));
        rods.add(new Curse(this));
        rods.add(new Drought(this));
        rods.add(new Drown(this));
        rods.add(new Ender(this));
        rods.add(new Explosion(this));
        rods.add(new Fire(this));
        rods.add(new Flight(this));
        rods.add(new Ghast(this));
        rods.add(new Health(this));
        rods.add(new Hunger(this));
        rods.add(new Instadiamond(this));
        rods.add(new Instairon(this));
        rods.add(new Instaleather(this));
        rods.add(new Invisibility(this));
        rods.add(new Jump(this));
        rods.add(new Knockback(this));
        rods.add(new Lightning(this));
        rods.add(new Minigun(this));
        rods.add(new Parkour(this));
        rods.add(new Resistance(this));
        rods.add(new Time(this));
        rods.add(new Torch(this));
        rods.add(new Weather(this));
        rods.add(new Zombie(this));
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
    
    public static void givePlayerRod(Rod rod, Player player, int amount) {
    	player.getInventory().addItem(rod.getItem(amount));
    }
    
    public static void addRod(Rod rod) {
    	rods.add(rod);
    	Utils.makeConfig(false);
    }
    
    public static Rod getRod(String name) {
    	for (Rod rod : rods)
    		if (rod.getName().equalsIgnoreCase(name))
    			return rod;
    	return null;
    }
    
    public static void debug(String msg) {
    	Bukkit.broadcastMessage(ChatColor.YELLOW + "" + '[' + ChatColor.AQUA + "Debug" + ChatColor.YELLOW + "] " + ChatColor.RED + msg);
    }
    
    
    public static List<Material> noFire = Arrays.asList(new Material[]{
    		Material.SIGN, Material.SIGN_POST, Material.WALL_SIGN,
    		Material.CHEST, Material.WORKBENCH, Material.LEVER,
    		Material.WOOD_BUTTON, Material.STONE_BUTTON, Material.WOODEN_DOOR
    });
    
}