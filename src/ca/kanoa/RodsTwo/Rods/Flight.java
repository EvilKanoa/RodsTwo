<<<<<<< HEAD
package ca.kanoa.rodstwo.Rods;
=======
package ca.kanoa.rodstwo.rods;
>>>>>>> dev

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ca.kanoa.rodstwo.RodsTwo;
<<<<<<< HEAD
import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;
=======
import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;
>>>>>>> dev

public class Flight extends Rod {

	public Flight() throws Exception {
	    super("Flight", 2, 280, new ConfigOptions(new String[]{"flight_time"}, new Object[]{2.5d}), 2500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("FFF", "FBF", "FFF").setIngredient('F', Material.FEATHER).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		if (player.isFlying())
			return false;
		final boolean wasAllowedFlight = player.getAllowFlight();
		player.teleport(player.getLocation().add(0, 0.5, 0));
		player.setAllowFlight(true);
		player.setFlying(true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int) (config.getDouble("flight_time") * 20), -1));
		final Player p1 = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			@Override
			public void run() {
				p1.setFlying(false);
				p1.setAllowFlight(wasAllowedFlight);
			}}, (long)(config.getDouble("flight_time") * 20));
	    return true; 
	}
}
