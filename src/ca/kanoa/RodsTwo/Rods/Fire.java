package ca.kanoa.rodstwo.rods;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;

public class Fire extends Rod {

	public Fire() throws Exception {
	    super("Fire", 1, 280, new ConfigOptions(), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("CBG").setIngredient('C', Material.COAL).setIngredient('G', Material.SULPHUR).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	    player.launchProjectile(SmallFireball.class);
		return true; 
	}
}
