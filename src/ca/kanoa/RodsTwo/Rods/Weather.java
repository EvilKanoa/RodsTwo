package ca.kanoa.rodstwo.Rods;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;

public class Weather extends Rod {

	public Weather(Plugin plugin) throws Exception {
	    super("Weather", 4, 280, new ConfigOptions(), 3000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" I ", "SBS", " I ").setIngredient('S', Material.SNOW_BALL).setIngredient('I', Material.IRON_INGOT).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		World world = player.getWorld();
		if (world.hasStorm()) {
			world.setStorm(false);
			world.setThundering(false);
			player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
			player.sendMessage("" + ChatColor.ITALIC + ChatColor.DARK_AQUA + "The bad weather will be ceased!");
		}
		else {
			world.setStorm(true);
			world.setThundering(true);
			player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
			player.sendMessage("" + ChatColor.ITALIC + ChatColor.DARK_AQUA + "Here comes the rain...");
		}
	    return true; 
	}
}
