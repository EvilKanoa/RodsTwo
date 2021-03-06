package ca.kanoa.rodstwo.rods;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.config.ConfigOptions;

public class Default extends Rod {

	public Default() throws Exception {
	    super("Debug", 1, 280, new ConfigOptions(new String[]{}, new Object[]{}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DBD", "DDD").setIngredient('D', Material.SPONGE).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	    return false; 
	}
}
