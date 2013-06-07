package ca.kanoa.RodsTwo.Rods;

import java.util.Random;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

public class Curse extends Rod {

	private Random rand;

	public Curse(Plugin plugin) throws Exception {
		super("Curse", 1, 280, new ConfigOptions(new String[]{"random_potion_length", "random_potion_level"}, new Object[]{30, 2}), 2000);
		setRecipe(new ShapedRecipe(super.getItem()).shape("SSS", "RBR").setIngredient('S', Material.SPIDER_EYE).setIngredient('R', Material.ROTTEN_FLESH).setIngredient('B', Material.STICK));
	}

	@Override
	public boolean run(Player player, ConfigurationSection config) {
		
		Entity target = getTarget(player);
		if (target == null || !(target instanceof LivingEntity))
			return false;

		PotionEffectType type;
		switch (rand.nextInt(8) + 1) {
		case 1:   type = PotionEffectType.BLINDNESS;    break;
		case 2:   type = PotionEffectType.CONFUSION;    break;
		case 3:   type = PotionEffectType.HARM;         break;
		case 4:   type = PotionEffectType.HUNGER;       break;
		case 5:   type = PotionEffectType.POISON;       break;
		case 6:   type = PotionEffectType.WITHER;       break;
		case 7:   type = PotionEffectType.SLOW;         break;
		case 8:   type = PotionEffectType.WEAKNESS;     break;
		default:  type = PotionEffectType.HEAL;
		}
		PotionEffect potion = new PotionEffect(type, config.getInt("random_potion_length"), config.getInt("random_potion_level") - 1);
		
		((LivingEntity)target).addPotionEffect(potion);
		target.getWorld().playEffect(target.getLocation(), Effect.POTION_BREAK, 4);
		return true;
	}

	@Override
	public boolean enable(Server serv) {
		rand = new Random(serv.getSpawnRadius() * serv.getMaxPlayers() * 10);
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
