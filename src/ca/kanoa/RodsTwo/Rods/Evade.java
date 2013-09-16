<<<<<<< HEAD
package ca.kanoa.rodstwo.Rods;
=======
package ca.kanoa.rodstwo.rods;
>>>>>>> dev

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
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

public class Evade extends Rod {

	private Random rand;
	
	public Evade() throws Exception {
	    super("Evade", 1, 280, new ConfigOptions(new String[]{"max_teleport_distance", "max_fall_distance"}, new Object[]{8, 3}), 250);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DBD", "DDD").setIngredient('D', Material.SPONGE).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		Location toLoc = null;
		Location fromLoc = player.getLocation();
		int i = 0, x = 0, y = 0, z = 0; 
		int dist = config.getInt("max_teleport_distance"), maxFallDist = config.getInt("max_fall_distance");
		int	oX = fromLoc.getBlockX(), oY = fromLoc.getBlockY(), oZ = fromLoc.getBlockZ();
		while (toLoc == null && i < 200) {
			x = (rand.nextInt(dist * 2) - dist) + oX;
			y = (rand.nextInt(dist * 2) - dist) + oY;
			z = (rand.nextInt(dist * 2) - dist) + oZ;
			if (y > 0 &&
					fromLoc.getWorld().getBlockAt(x, y, z).getType() == Material.AIR && 
					fromLoc.getWorld().getBlockAt(x, y + 1, z).getType() == Material.AIR &&
					isBlockBelow(new Location(fromLoc.getWorld(), x, y, z), maxFallDist))
				toLoc = new Location(fromLoc.getWorld(), x + 0.5, y, z + 0.5, fromLoc.getYaw(), fromLoc.getPitch());
			i++;
		}
		
		if (toLoc != null) {
			player.teleport(toLoc);
			player.playSound(toLoc, Sound.GLASS, 0.5f, 1);
			return true;
		}
		else
			return false; 
	}
	
	private boolean isBlockBelow(Location loc, int max) {
		for (int i = 1; i <= max; i++)
			if (loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - i, loc.getBlockZ()).getType() != Material.AIR)
				return true;
		return false;
	}

	@Override
	public boolean enable(Server serv) {
		rand = new Random(1337);
		return true;
	}
}
