package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.RodsTwo;
import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Arrow extends Rod {

	public Arrow(Plugin plugin) throws Exception {
	    super("Arrow", 1, 280, new ConfigOptions(new String[]{"quiver_size"}, new Object[]{4}), 500, plugin);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("AAA", "ABA", "AAA").setIngredient('A', Material.ARROW).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		final Player p1 = player;
	    for (int i = 0; i < config.getInt("quiver_size"); i++)
	    	player.getServer().getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
				public void run() {
					p1.launchProjectile(org.bukkit.entity.Arrow.class);
				}}, i * 3);
		return true; 
	}
}
