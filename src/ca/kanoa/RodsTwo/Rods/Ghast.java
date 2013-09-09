package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

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
