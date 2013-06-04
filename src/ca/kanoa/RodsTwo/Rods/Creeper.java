package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Creeper extends Rod {

	public Creeper(Plugin plugin) throws Exception {
	    super("Creeper", 1, 280, new ConfigOptions(new String[]{"number_to_spawn", "max_distence"}, new Object[]{1, 50}), 500, plugin);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" G ", "GBG", " G ").setIngredient('G', Material.SULPHUR).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	   	for (int i = 0; i < config.getInt("number_to_spawn"); i++) {
	   		Location loc = player.getTargetBlock(null, config.getInt("max_distence")).getLocation();
	   		loc.setY(loc.getY() + (i / 2));
	   		player.getWorld().spawn(loc, org.bukkit.entity.Creeper.class);
	   	}
	   	return true;
	}
}
