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
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.config.ConfigOptions;

public class Jump extends Rod implements Listener {

	private Set<String> jumpees;
	
	public Jump() throws Exception {
	    super("Jump", 1, 280, new ConfigOptions(new String[]{"power"}, new Object[]{5.0d}), 2000);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" B ", " I ").setIngredient('I', Material.IRON_BOOTS).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		player.setVelocity(player.getVelocity().setY(config.getDouble("power") / 4));
		player.playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
		final String playerName = player.getName();
		jumpees.add(playerName);
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			@Override
			public void run() {
				if (jumpees.contains(playerName))
					jumpees.remove(playerName);
			}}, 140);
	    return true; 
	}
	
	@Override
	public boolean enable(Server serv) {
		jumpees = new HashSet<String>();
		Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
		return true;
	}
	
	@EventHandler
	public void onPlayerFall(EntityDamageEvent event) {
		if (event.getCause() == DamageCause.FALL && event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (jumpees.contains(p.getName())) {
				event.setCancelled(true);
				jumpees.remove(p.getName());
				p.playSound(p.getLocation(), Sound.FALL_BIG, 1, 1);
			}
		}
	}
}
