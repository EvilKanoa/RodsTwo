package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Ender extends Rod {

	public Ender(Plugin plugin) throws Exception {
	    super("Ender", 1, 280, new ConfigOptions(), 500, plugin);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("EBE").setIngredient('E', Material.ENDER_PEARL).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	    player.launchProjectile(EnderPearl.class);
		return true; 
	}
}
