package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Cake extends Rod {

	public Cake(Plugin plugin) throws Exception {
	    super("Cake", 1, 280, new ConfigOptions(), 1000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("CCC", "CBC", "CCC").setIngredient('C', Material.CAKE).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		Location loc = player.getLocation().add(0, 1, 0);
		ItemStack cake = new ItemStack(Material.CAKE, 1);
		World world = loc.getWorld();
		world.dropItemNaturally(loc.add(1, 0, 1), cake);
		world.dropItemNaturally(loc.add(-1, 0, 1), cake);
		world.dropItemNaturally(loc.add(1, 0, -1), cake);
		world.dropItemNaturally(loc.add(-1, 0, -1), cake);
		player.playSound(loc, Sound.CLICK, 1, 1);
	    return false; 
	}
}
