package ca.kanoa.rodstwo.rods;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.config.ConfigOptions;

public class Spleef extends Rod {

	private static List<Material> spleefable = Arrays.asList(
			new Material[]{
					Material.GRASS,
					Material.DIRT,
					Material.STONE,
					Material.WATER,
					Material.SAND,
					Material.STATIONARY_WATER
			});
	
	public Spleef() {
		super("Spleef", 280, new ConfigOptions(new String[]{"depth", "range"}, new Object[]{25, 30}), 5000, 3);
		setRecipe(new ShapedRecipe(super.getItem()).shape(" D ", "DDD", " D ").setIngredient('D', Material.SPONGE));
	}

	@Override
	public boolean run(Player player, ConfigurationSection config) {
		@SuppressWarnings("deprecation")
		Location loc = player.getTargetBlock(null, config.getInt("range")).getLocation().add(0, 1, 0);
		for (int x = loc.getBlockX() - 1; x <= loc.getBlockX() + 1; x++) {
			for (int z = loc.getBlockZ() - 1; z <= loc.getBlockZ() + 1; z++) {
				for (int y = loc.getBlockY() - config.getInt("depth"); y <= loc.getBlockY(); y++) {
					Block block = loc.getWorld().getBlockAt(x, y, z);
					if (spleefable.contains(block.getType())) {
						block.setType(Material.AIR);
					}
				}
			}
		}
		return true;
	}
	
}
