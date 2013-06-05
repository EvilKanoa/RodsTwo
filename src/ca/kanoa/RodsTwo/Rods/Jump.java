package ca.kanoa.RodsTwo.Rods;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

public class Jump extends Rod {

	public Jump(Plugin plugin) throws Exception {
	    super("Jump", 1, 280, new ConfigOptions(new String[]{"power"}, new Object[]{5.0d}), 2000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" B ", " I ").setIngredient('I', Material.IRON_BOOTS).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		player.setVelocity(player.getVelocity().setY(config.getDouble("power") / 4));
		player.playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
	    return true; 
	}
}
