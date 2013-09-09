package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Instairon extends Rod {

	public Instairon() throws Exception {
	    super("Insta Iron", 1, 280, new ConfigOptions(), 2000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" H ", "CBL", " F ").setIngredient('H', Material.IRON_HELMET).setIngredient('C',  Material.IRON_CHESTPLATE)
	    		.setIngredient('L', Material.IRON_LEGGINGS).setIngredient('F', Material.IRON_BOOTS).setIngredient('B', Material.STICK));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		if (!isEmpty(player.getInventory().getArmorContents())) {
			player.sendMessage(ChatColor.GREEN + "You already have armor on, silly!");
			return false; 
		}
		player.getInventory().setArmorContents(new ItemStack[]{
				new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), 
				new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET)});
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.ANVIL_USE, 1, 1);
		return true;
	}
	
	private boolean isEmpty(ItemStack[] contents) {
		for (ItemStack i : contents)
			if (i.getType() != Material.AIR && i != null)
				return false;
		return true;
	}
}
