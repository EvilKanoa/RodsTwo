package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.RodsTwo;
import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Flight extends Rod {

	public Flight(Plugin plugin) throws Exception {
	    super("Flight", 2, 280, new ConfigOptions(new String[]{"flight_time"}, new Object[]{2.5d}), 2500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("FFF", "FBF", "FFF").setIngredient('F', Material.FEATHER).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		if (player.isFlying())
			return false;
		player.setFlying(true);
		final Player p1 = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			@Override
			public void run() {
				p1.setFlying(false);
			}}, (long)(config.getDouble("flight_time") * 20));
	    return true; 
	}
}
