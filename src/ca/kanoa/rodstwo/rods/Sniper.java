package ca.kanoa.rodstwo.rods;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.config.ConfigOptions;

public class Sniper extends Rod implements Listener {

	private Set<org.bukkit.entity.Arrow> arrows;
	private ConfigurationSection config;
	
	public Sniper() throws Exception {
	    super("Sniper", 1, 280, new ConfigOptions(new String[]{"power", "damage"}, new Object[]{10, 15}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DBD", "DDD").setIngredient('D', Material.SPONGE).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		this.config = config;
		final org.bukkit.entity.Arrow arrow = player.launchProjectile(org.bukkit.entity.Arrow.class);
		player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
		final Vector velocity = player.getEyeLocation().getDirection().multiply(config.getInt("power"));
		arrow.setVelocity(velocity);
		arrow.setBounce(false);
		arrows.add(arrow);
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			@Override
			public void run() {
				if (arrows.contains(arrow)) {
					arrow.setVelocity(velocity);
					Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, this, 1);
				}
			}}, 1);
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			public void run() {
				if (arrows.contains(arrow)) {
					arrow.remove();
					arrows.remove(arrow);
				}
			}}, 30L);
	    return true; 
	}
	
	@Override
	public boolean enable(Server serv) {
		this.arrows = new HashSet<org.bukkit.entity.Arrow>();
		Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
		return true;
	}
	
	@EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
	public void onArrowHit(ProjectileHitEvent event){
		if(event.getEntity() instanceof org.bukkit.entity.Arrow){
			final org.bukkit.entity.Arrow a = (org.bukkit.entity.Arrow) event.getEntity();
			if (arrows.contains(a)) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
					public void run() {
						a.remove();
						arrows.remove(a);
					}}, 1);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
	public void onArrowHitEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof org.bukkit.entity.Arrow &&
				arrows.contains(event.getDamager())) {
			event.setDamage(config.getInt("damage"));
		}
	}
	
}
