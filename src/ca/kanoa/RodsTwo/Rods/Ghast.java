package ca.kanoa.rodstwo.rods;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;

public class Ghast extends Rod {

	public Ghast() throws Exception {
	    super("Ghast", 1, 280, new ConfigOptions(), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("GBS").setIngredient('S', Material.SULPHUR).setIngredient('G', Material.GHAST_TEAR).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	    player.launchProjectile(Fireball.class);
		return true; 
	}
}
