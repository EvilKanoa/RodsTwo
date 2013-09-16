package ca.kanoa.rodstwo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ca.kanoa.rodstwo.Helpers.CommandExecutor;
import ca.kanoa.rodstwo.Helpers.Utils;
import ca.kanoa.rodstwo.Helpers.VaultManager;
import ca.kanoa.rodstwo.Listeners.CastListener;
import ca.kanoa.rodstwo.Listeners.CraftListener;
import ca.kanoa.rodstwo.Listeners.SignListener;
import ca.kanoa.rodstwo.Objects.Rod;
import ca.kanoa.rodstwo.Rods.*;

public class RodsTwo extends JavaPlugin implements Listener{

    public static List<Rod> rods;
    public static Map<String, Long> cooldowns;
    public static PluginDescriptionFile pdf;
    private Comparator<Rod> rodComparator = new Comparator<Rod>(){
		@Override
		public int compare(Rod rod1, Rod rod2) {
			int chosen = rod1.getName().equalsIgnoreCase(rod2.getName()) ? 0 : 2;
			int index = 0;
			while (chosen == 2) {
				char c1 = rod1.getName().charAt(index), c2 = rod2.getName().charAt(index);
				if (Character.toLowerCase(c1) != Character.toLowerCase(c2))
					chosen = Character.getNumericValue(Character.toLowerCase(c1)) < Character.getNumericValue(Character.toLowerCase(c2)) ? -1 : 1;
				index++;
				if (index > rod1.getName().length() - 1) 
					chosen = -1;
				else if (index > rod2.getName().length() - 1)
					chosen = 1;
			}
			return chosen;
		}};
    public Logger logger;
    public FileConfiguration rodConfig;
    public static RodsTwo plugin;
    public static boolean useMobDeathAsPlayer;

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
        
        useMobDeathAsPlayer = Bukkit.getPluginManager().isPluginEnabled("Batman API");
        
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
        rods.add(new Time());
        rods.add(new Torch());
        rods.add(new Weather());
        rods.add(new Zombie());
        //TODO: Make more rods!!!
        
        for (Rod rod : RodLoader.getRods(new File(getDataFolder(), "rods")))
        		rods.add(rod);
        
        Collections.sort(rods, rodComparator);
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
    
    public static void main(String[] args) {
    	JOptionPane.showMessageDialog(null, "This is a plugin meant to be used with CraftBukkit.", "Error", 1);
    }
    
    public static List<Material> noFire = Arrays.asList(new Material[]{
    		Material.SIGN, Material.SIGN_POST, Material.WALL_SIGN,
    		Material.CHEST, Material.WORKBENCH, Material.LEVER,
    		Material.WOOD_BUTTON, Material.STONE_BUTTON, Material.WOODEN_DOOR
    });

	public static double getVersion() {
		try {
			return Double.parseDouble(pdf.getVersion());
		} catch (NumberFormatException e) {
			return 1.0;
		}
	}
    
}