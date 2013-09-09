package ca.kanoa.RodsTwo.Rods;

import java.util.HashSet;
import java.util.Set;

import ca.kanoa.RodsTwo.RodsTwo;
import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ShapedRecipe;

public class Arrow extends Rod implements Listener {

	private Set<org.bukkit.entity.Arrow> arrows;

	public Arrow() throws Exception {
		super("Arrow", 1, 280, new ConfigOptions(new String[]{"quiver_size"}, new Object[]{4}), 500);
		setRecipe(new ShapedRecipe(super.getItem()).shape("AAA", "ABA", "AAA").setIngredient('A', Material.ARROW).setIngredient('B', Material.STICK));
	}

	@Override
	public boolean run(Player player, ConfigurationSection config) {
		final Player p1 = player;
		for (int i = 0; i < config.getInt("quiver_size"); i++)
			player.getServer().getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
				public void run() {
					org.bukkit.entity.Arrow a = p1.launchProjectile(org.bukkit.entity.Arrow.class);
					arrows.add(a);
				}}, i * 3);
		return true;
	}

	@Override
	public boolean enable(Server serv) {
		this.arrows = new HashSet<org.bukkit.entity.Arrow>();
		Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
		return true;
	}

	@EventHandler
	public void onArrowHit(ProjectileHitEvent event){
		if(event.getEntity() instanceof org.bukkit.entity.Arrow){
			final org.bukkit.entity.Arrow a = (org.bukkit.entity.Arrow) event.getEntity();
			if (arrows.contains(a)) {
				arrows.remove(a);
				Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
					public void run() {
						a.remove();
					}}, 1);
			}
		}
	}
}
