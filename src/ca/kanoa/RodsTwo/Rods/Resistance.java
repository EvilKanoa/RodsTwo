package ca.kanoa.rodstwo.Rods;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;

public class Resistance extends Rod {

	public Resistance() throws Exception {
	    super("Resistance", 1, 280, new ConfigOptions(new String[]{"level", "length"}, new Object[]{1, 30}), 3000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" M ", " B ", " S ").setIngredient('M', Material.MAGMA_CREAM).setIngredient('S', Material.SLIME_BALL).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, config.getInt("length") * 20, config.getInt("level") - 1));
	    return true; 
	}
}
