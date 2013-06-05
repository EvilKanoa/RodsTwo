package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Explosion extends Rod {

	public Explosion(Plugin plugin) throws Exception {
	    super("Explosion", 1, 280, new ConfigOptions(new String[]{"maax_distence", "power"}, new Object[]{50, 4.0D}), 500, plugin);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" B ", " E ").setIngredient('E', Material.TNT).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		player.getWorld().createExplosion(player.getTargetBlock(null, config.getInt("max_distence")).getLocation(), (float) config.getDouble("power"), true);
	    return true; 
	}
}
