package ca.kanoa.rodstwo.Rods;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;

public class Invisibility extends Rod {

	public Invisibility(Plugin plugin) throws Exception {
	    super("Invisibility", 1, 280, new ConfigOptions(new String[]{"potion_length"}, new Object[]{30}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("CBC").setIngredient('C', Material.GOLDEN_CARROT).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		if (player.hasPotionEffect(PotionEffectType.INVISIBILITY))
			return false;
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, config.getInt("potion_length") * 20, 0));
		player.playSound(player.getLocation().add(0, 4, 0), Sound.LEVEL_UP, 1, 1);
		return true;
	}
}
