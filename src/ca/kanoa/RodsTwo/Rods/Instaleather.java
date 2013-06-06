package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Instaleather extends Rod {

	public Instaleather(Plugin plugin) throws Exception {
	    super("Instaleather", 1, 280, new ConfigOptions(), 2000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" H ", "CBL", " F ").setIngredient('H', Material.LEATHER_HELMET).setIngredient('C',  Material.LEATHER_CHESTPLATE)
	    		.setIngredient('L', Material.LEATHER_LEGGINGS).setIngredient('F', Material.LEATHER_BOOTS).setIngredient('B', Material.STICK));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		if (!isEmpty(player.getInventory().getArmorContents())) {
			player.sendMessage(ChatColor.GREEN + "You already have armor on, silly!");
			return false; 
		}
		player.getInventory().setArmorContents(new ItemStack[]{
				new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.LEATHER_LEGGINGS), 
				new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_HELMET)});
		player.updateInventory();
		return true;
	}
	
	private boolean isEmpty(ItemStack[] contents) {
		for (ItemStack i : contents)
			if (i.getType() != Material.AIR && i != null)
				return false;
		return true;
	}
}
