package ca.kanoa.rodstwo.Rods;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;

public class Drown extends Rod {

	public Drown(Plugin plugin) throws Exception {
	    super("Drown", 1, 280, new ConfigOptions(new String[]{"max_distence", "drowning_length"}, new Object[]{20, 4}), 5000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DBD", "DDD").setIngredient('D', Material.SPONGE).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		final Location loc = player.getTargetBlock(null, config.getInt("max_distence")).getLocation().add(0, 2, 0);
		final boolean[][][] placed = new boolean[3][3][3];
		for (int x = loc.getBlockX() - 1; x <= loc.getBlockX() + 1; x++)
			for (int z = loc.getBlockZ() - 1; z <= loc.getBlockZ() + 1; z++)
				for (int y = loc.getBlockY() - 1; y <= loc.getBlockY() + 1; y++)
					if (loc.getWorld().getBlockAt(x, y, z).getType() == Material.AIR) {
						loc.getWorld().getBlockAt(x, y, z).setType(Material.WATER);
						placed[x - loc.getBlockX() + 1][y - loc.getBlockY() + 1][z - loc.getBlockZ() + 1] = true;
					} else {
						placed[x - loc.getBlockX() + 1][y - loc.getBlockY() + 1][z - loc.getBlockZ() + 1] = false;
					}
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			public void run() {
				for (int x = loc.getBlockX() - 1; x <= loc.getBlockX() + 1; x++)
					for (int z = loc.getBlockZ() - 1; z <= loc.getBlockZ() + 1; z++)
						for (int y = loc.getBlockY() - 1; y <= loc.getBlockY() + 1; y++)
							if (placed[x - loc.getBlockX() + 1][y - loc.getBlockY() + 1][z - loc.getBlockZ() + 1] == true)
								loc.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
			}}, config.getInt("drowning_length") * 20);
	    return true; 
	}
}
