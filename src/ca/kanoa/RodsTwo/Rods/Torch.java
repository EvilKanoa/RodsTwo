<<<<<<< HEAD
package ca.kanoa.rodstwo.Rods;
=======
package ca.kanoa.rodstwo.rods;
>>>>>>> dev

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

<<<<<<< HEAD
import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;
=======
import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;
>>>>>>> dev

public class Torch extends Rod {

	public Torch() throws Exception {
	    super("Torch", 1, 280, new ConfigOptions(new String[]{"max_distence"}, new Object[]{50}), 2000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("TBT").setIngredient('T', Material.TORCH).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		Location loc = player.getTargetBlock(null, config.getInt("max_distence")).getLocation();
		int x = loc.getBlockX(), z = loc.getBlockZ();
		World world = loc.getWorld();
		for (int y = loc.getBlockY(); y <= loc.getBlockY() + 3; y++)
			if (world.getBlockAt(x, y, z).getType() == Material.AIR && (world.getBlockAt(x, y - 1, z).getType() != Material.AIR || 
			world.getBlockAt(x + 1, y, z).getType() != Material.AIR || world.getBlockAt(x - 1, y, z).getType() != Material.AIR ||
			world.getBlockAt(x, y, z + 1).getType() != Material.AIR || world.getBlockAt(x, y, z - 1).getType() != Material.AIR)) {
				loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).setType(Material.TORCH);
				player.playSound(player.getLocation(), Sound.BURP, 1, 1);
				return true;
			}
	    return false; 
	}
}
