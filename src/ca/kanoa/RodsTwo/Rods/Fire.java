package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Fire extends Rod {

	public Fire(Plugin plugin) throws Exception {
	    super("Fire", 1, 280, new ConfigOptions(), 500, plugin);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("CBG").setIngredient('C', Material.COAL).setIngredient('G', Material.SULPHUR).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	    player.launchProjectile(SmallFireball.class);
		return true; 
	}
}
