package ca.kanoa.rodstwo.Rods;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;

public class Broadcast extends Rod {

	public Broadcast() throws Exception {
	    super("Broadcast", 1, 280, new ConfigOptions(new String[]{"message"}, new Object[]{"%%PLAYER%% is a legend!"}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" G ", "GBG", " G ").setIngredient('G', Material.GOLD_INGOT).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		Bukkit.broadcastMessage(config.getString("message").replace("%%PLAYER%%", player.getName()));
	    return true; 
	}
}
