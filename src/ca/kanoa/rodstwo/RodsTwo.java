package ca.kanoa.rodstwo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ca.kanoa.rodstwo.config.Version;
import ca.kanoa.rodstwo.helpers.CommandExecutor;
import ca.kanoa.rodstwo.helpers.RodComparator;
import ca.kanoa.rodstwo.helpers.Utils;
import ca.kanoa.rodstwo.helpers.VaultManager;
import ca.kanoa.rodstwo.listeners.CastListener;
import ca.kanoa.rodstwo.listeners.CooldownItemListener;
import ca.kanoa.rodstwo.listeners.CraftListener;
import ca.kanoa.rodstwo.listeners.ItemListener;
import ca.kanoa.rodstwo.listeners.SignListener;
import ca.kanoa.rodstwo.rods.*;

public class RodsTwo extends JavaPlugin implements Listener {

    public static List<Rod> rods;
    public PluginDescriptionFile pdf;
    public Logger logger;
    public FileConfiguration rodConfig;
    private static RodsTwo instance;

    public void onEnable() {
    	rods = new ArrayList<Rod>();
        pdf = getDescription();
        logger = getLogger();
        instance = this;
        
        getCommand("lightningrod").setExecutor(new CommandExecutor());
        Bukkit.getPluginManager().registerEvents(new CastListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
        Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
        Bukkit.getPluginManager().registerEvents(new CooldownItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemListener(), this);
        
        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
        	VaultManager.eco = VaultManager.setupEconomy();
        } else {
        	getLogger().warning("Vault has not been enabled, using signs will not cost any money!");
        }
        
        try {
            addRods();
        } catch (Exception e) {
            e.printStackTrace();
        }

        rodConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "rods.yml"));
        Utils.loadRodOptions();
        Utils.addRecipes();

        Utils.initializeRods();

        logger.info(pdf.getName() + " v" + pdf.getVersion() + " enabled");
    }

    public List<Rod> getRods(){
        return rods;
    }

    private void addRods() throws Exception {
        //TODO: Make more rods!!!
        rods.add(new Arrow());
        rods.add(new Broadcast());
        rods.add(new Companion());
        rods.add(new Cake());
        rods.add(new Creeper());
        rods.add(new Curse());
        rods.add(new Drought());
        rods.add(new Drown());
        rods.add(new Ender());
        rods.add(new Explosion());
        rods.add(new Evade());
        rods.add(new Fire());
        rods.add(new Firework());
        rods.add(new Flight());
        rods.add(new Ghast());
        rods.add(new God());
        rods.add(new Health());
        rods.add(new Hunger());
        rods.add(new Instadiamond());
        rods.add(new Instairon());
        rods.add(new Instaleather());
        rods.add(new Invisibility());
        rods.add(new Jump());
        rods.add(new Knockback());
        rods.add(new Lightning());
        rods.add(new Minigun());
        rods.add(new Parkour());
        rods.add(new Pickpocket());
        rods.add(new Resistance());
        rods.add(new Sniper());
        rods.add(new Spleef());
        rods.add(new Time());
        rods.add(new Torch());
        rods.add(new Weather());
        rods.add(new Zombie());
        //TODO: Make more rods!!!
        
        getLogger().info("External rod loading is currently disabled");
        /**for (Rod rod : RodLoader.getRods(new File(getDataFolder(), "rods"))) {
        	rods.add(rod);
        }**/
        
        Collections.sort(rods, new RodComparator());
        Utils.makeConfig(false);
    }
    
    public static void addRods(Rod[] rods) {
    	for (Rod rod : rods)
    		ca.kanoa.rodstwo.RodsTwo.rods.add(rod);
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
    	System.out.println("[Debug] " + msg);
    }
    
    public static RodsTwo getInstance() {
    	return instance;
    }
    
    public static List<Material> noFire = Arrays.asList(new Material[]{
    		Material.SIGN, Material.SIGN_POST, Material.WALL_SIGN,
    		Material.CHEST, Material.WORKBENCH, Material.LEVER,
    		Material.WOOD_BUTTON, Material.STONE_BUTTON, Material.WOODEN_DOOR
    });

	public Version getVersion() {
		return Version.parseString(pdf.getVersion());
	}
    
}