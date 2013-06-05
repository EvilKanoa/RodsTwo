package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Time extends Rod {

	public Time(Plugin plugin) throws Exception {
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
