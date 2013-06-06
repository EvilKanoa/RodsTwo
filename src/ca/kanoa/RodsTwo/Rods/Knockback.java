package ca.kanoa.RodsTwo.Rods;

import java.util.Arrays;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Knockback extends Rod {

	public Knockback(Plugin plugin) throws Exception {
	    super("Knockback", 1, 280, new ConfigOptions(new String[]{"knockback_level", "durability_left_on_item"}, new Object[]{5, 1}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("SBS").setIngredient('S', Material.WOOD_SWORD).setIngredient('B', Material.STICK));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		ItemStack sword = new ItemStack(Material.WOOD_SWORD, 1);
		ItemMeta im = sword.getItemMeta();
		im.addEnchant(Enchantment.KNOCKBACK, config.getInt("knockback_level"), true);
		im.setLore(Arrays.asList(new String[]{"Knock your enemys away!"}));
		im.setDisplayName("" + ChatColor.RESET + ChatColor.BLUE + "Knockerbacker");
		sword.setItemMeta(im);
		sword.setDurability((short) (60 - config.getInt("durability_left_on_item")));
		player.getInventory().addItem(sword);
		player.updateInventory();
	    return true; 
	}
}