package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.RodsTwo;
import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

public class Drought extends Rod {

	public Drought() throws Exception {
	    super("Drought", 4, 280, new ConfigOptions(new String[]{"max_distence"}, new Object[]{20}), 3000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("SBS").setIngredient('S', Material.SPONGE).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		final Location loc = player.getTargetBlock(null, config.getInt("max_distence")).getLocation().add(0, 2, 0);
		for (int x = loc.getBlockX() - 2; x <= loc.getBlockX() + 2; x++)
			for (int z = loc.getBlockZ() - 2; z <= loc.getBlockZ() + 2; z++)
				for (int y = loc.getBlockY() - 2; y <= loc.getBlockY() + 2; y++) 
					if (loc.getWorld().getBlockAt(x, y, z).getType() == Material.WATER || 
					loc.getWorld().getBlockAt(x, y, z).getType() == Material.STATIONARY_WATER ||
					loc.getWorld().getBlockAt(x, y, z).getType() == Material.ICE)
						loc.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
		player.playSound(loc, Sound.ITEM_BREAK, 2, 1);
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			public void run() {
				for (int x = loc.getBlockX() - 3; x <= loc.getBlockX() + 3; x++)
					for (int z = loc.getBlockZ() - 3; z <= loc.getBlockZ() + 3; z++)
						for (int y = loc.getBlockY() - 3; y <= loc.getBlockY() + 3; y++) 
							if (loc.getWorld().getBlockAt(x, y, z).getType() == Material.WATER || 
							loc.getWorld().getBlockAt(x, y, z).getType() == Material.STATIONARY_WATER ||
							loc.getWorld().getBlockAt(x, y, z).getType() == Material.ICE)
								loc.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
			}}, 20);
		return true;
	}
}