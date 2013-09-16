package ca.kanoa.rodstwo.rods;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;

public class Instadiamond extends Rod {

	public Instadiamond() throws Exception {
	    super("Insta Diamond", 1, 280, new ConfigOptions(), 2000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" H ", "CBL", " F ").setIngredient('H', Material.DIAMOND_HELMET).setIngredient('C',  Material.DIAMOND_CHESTPLATE)
	    		.setIngredient('L', Material.DIAMOND_LEGGINGS).setIngredient('F', Material.DIAMOND_BOOTS).setIngredient('B', Material.STICK));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		if (!isEmpty(player.getInventory().getArmorContents())) {
			player.sendMessage(ChatColor.GREEN + "You already have armor on, silly!");
			return false; 
		}
		player.getInventory().setArmorContents(new ItemStack[]{
				new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.DIAMOND_LEGGINGS), 
				new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_HELMET)});
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
