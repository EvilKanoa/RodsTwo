package ca.kanoa.rodstwo.rods;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;

public class Time extends Rod {

	public Time() throws Exception {
	    super("Time", 1, 280, new ConfigOptions(new String[]{}, new Object[]{}), 100);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" B ", " C ").setIngredient('C', Material.WATCH).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		player.getWorld().setTime(player.getWorld().getTime() + 6000 >= 24000 ? player.getWorld().getTime() + 6000 - 24000 : player.getWorld().getTime() + 6000);
		player.playSound(player.getLocation(), Sound.PORTAL_TRAVEL, 1, 1);
	    return true; 
	}
}
