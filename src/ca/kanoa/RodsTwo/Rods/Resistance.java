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

public class Resistance extends Rod {

	public Resistance(Plugin plugin) throws Exception {
	    super("Resistance", 1, 280, new ConfigOptions(new String[]{"level", "length"}, new Object[]{1, 30}), 10000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" M ", " B ", " S ").setIngredient('M', Material.MAGMA_CREAM).setIngredient('S', Material.SLIME_BALL).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, config.getInt("length") * 20, config.getInt("level") - 1));
	    return true; 
	}
}
