package ca.kanoa.rodstwo.Rods;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;

public class Default extends Rod {

	public Default(Plugin plugin) throws Exception {
	    super("Debug", 1, 280, new ConfigOptions(new String[]{}, new Object[]{}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DBD", "DDD").setIngredient('D', Material.SPONGE).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	    return false; 
	}
}
