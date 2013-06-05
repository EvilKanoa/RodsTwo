package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Broadcast extends Rod {

	public Broadcast(Plugin plugin) throws Exception {
	    super("Broadcast", 1, 280, new ConfigOptions(new String[]{"message"}, new Object[]{"%%PLAYER%% is a legend!"}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" G ", "GBG", " G ").setIngredient('G', Material.GOLD_INGOT).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		Bukkit.broadcastMessage(config.getString("message").replace("%%PLAYER%%", player.getName()));
	    return true; 
	}
}
