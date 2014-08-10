package ca.kanoa.rodstwo.rods;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.config.ConfigOptions;

public class Ender extends Rod {

	public Ender() throws Exception {
	    super("Ender", 1, 280, new ConfigOptions(), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("EBE").setIngredient('E', Material.ENDER_PEARL).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	    player.launchProjectile(EnderPearl.class);
		return true; 
	}
}
