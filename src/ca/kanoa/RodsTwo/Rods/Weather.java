package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Weather extends Rod {

	public Weather(Plugin plugin) throws Exception {
	    super("Weather", 4, 280, new ConfigOptions(), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" I ", "SBS", " I ").setIngredient('S', Material.SNOW_BALL).setIngredient('I', Material.IRON_INGOT).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		World world = player.getWorld();
		if (world.hasStorm()) {
			world.setStorm(false);
			world.setThundering(false);
			player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
		}
		else {
			world.setStorm(true);
			world.setThundering(true);
			player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
		}
	    return true; 
	}
}
