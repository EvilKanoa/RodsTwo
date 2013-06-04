package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Parkour extends Rod {

	public Parkour(Plugin plugin) throws Exception {
	    super("Debug", 1, 280, new ConfigOptions(new String[]{"time_of_jump", "time_of_speed", "jump_level", "speed_level"}, new Object[]{20, 20, 1, 2}), 500, plugin);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("SBU").setIngredient('S', Material.SUGAR).setIngredient('U', Material.SLIME_BALL).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		player.removePotionEffect(PotionEffectType.JUMP);
		player.removePotionEffect(PotionEffectType.SPEED);
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, config.getInt("time_of_jump") * 20, config.getInt("jump_level")));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, config.getInt("time_of_speed") * 20, config.getInt("speed_level")));
	    return true; 
	}
}
