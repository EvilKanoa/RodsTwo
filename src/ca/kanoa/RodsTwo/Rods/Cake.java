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

public class Cake extends Rod {

	public Cake() throws Exception {
	    super("Cake", 1, 280, new ConfigOptions(), 1000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("CCC", "CBC", "CCC").setIngredient('C', Material.CAKE).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		Location loc = player.getLocation().add(0, 0.2, 0);
		ItemStack cake = new ItemStack(Material.CAKE, 1);
		World world = loc.getWorld();
		world.dropItem(loc.add(1, 0, 1), cake);
		world.dropItem(loc.add(-1, 0, 1), cake);
		world.dropItem(loc.add(1, 0, -1), cake);
		world.dropItem(loc.add(-1, 0, -1), cake);
		player.playSound(loc, Sound.CLICK, 1, 1);
	    return true; 
	}
}
