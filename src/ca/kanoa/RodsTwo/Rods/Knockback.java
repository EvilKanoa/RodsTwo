package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Knockback extends Rod {

	public Knockback(Plugin plugin) throws Exception {
	    super("Knockback", 1, 280, new ConfigOptions(new String[]{}, new Object[]{}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" D ", " B ", " D ").setIngredient('D', Material.IRON_SWORD).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		if (isFull(player))
			return false;
	    return true; 
	}

	private boolean isFull(Player player) {
		for (ItemStack is : player.getInventory().getContents())
			if (is.getType() == Material.AIR)
				return false;
		return true;
	}
}