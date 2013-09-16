package ca.kanoa.rodstwo.Rods;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;

import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;

public class Pickpocket extends Rod {

	public Pickpocket(Plugin plugin) throws Exception {
	    super("Pick Pocket", 1, 280, new ConfigOptions(new String[]{}, new Object[]{}), 2300);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DBD", "DDD").setIngredient('D', Material.SPONGE).setIngredient('B', Material.STICK));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		Entity target = getTarget(player);
		
		if (target == null)
			return false;
		else if (!(target instanceof Player))
			return false;
		
		Player targetPlayer = (Player) target;
		
		if (targetPlayer.getInventory().getItemInHand().getType() == null || targetPlayer.getInventory().getItemInHand().getType() == Material.AIR)
			return false;
		
		ItemStack item = targetPlayer.getInventory().getItemInHand();
		targetPlayer.getInventory().remove(item);
		if (item.getAmount() > 1) {
			item.setAmount(item.getAmount() - 1);
			targetPlayer.getInventory().addItem(item);
		}
		targetPlayer.updateInventory();
		item.setAmount(1);
		
		player.playSound(player.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 1);
		player.getInventory().addItem(item);
		player.updateInventory();

		item.setAmount(1);
		
		return true;
	}
	
	private Entity getTarget(Player player) {
		BlockIterator iterator = new BlockIterator(player.getWorld(), player
				.getLocation().toVector(), player.getEyeLocation()
				.getDirection(), 0, 100);
		Entity target = null;
		while (iterator.hasNext()) {
			Block item = iterator.next();
			for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
				int acc = 2;
				for (int x = -acc; x < acc; x++)
					for (int z = -acc; z < acc; z++)
						for (int y = -acc; y < acc; y++)
							if (entity.getLocation().getBlock()
									.getRelative(x, y, z).equals(item)) 
								return target = entity;
			}
		}
		return target;
	}
}
