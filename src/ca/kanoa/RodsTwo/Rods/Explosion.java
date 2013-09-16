<<<<<<< HEAD
package ca.kanoa.rodstwo.Rods;
=======
package ca.kanoa.rodstwo.rods;
>>>>>>> dev

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.RodsTwo;
<<<<<<< HEAD
import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;
=======
import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;
>>>>>>> dev

public class Explosion extends Rod implements Listener {

	private HashMap<TNTPrimed, String> bombSites;
	
	public Explosion() throws Exception {
	    super("Explosion", 1, 280, new ConfigOptions(new String[]{"max_distence", "power", "enable_fire"}, new Object[]{50, 4.0D, true}), 500);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" B ", " E ").setIngredient('E', Material.TNT).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		Location loc = player.getTargetBlock(null, config.getInt("max_distence")).getLocation();
		final TNTPrimed tnt = player.getWorld().spawn(loc.add(0, 1, 0), TNTPrimed.class);
		tnt.setYield((float) config.getDouble("power"));
		tnt.setIsIncendiary(config.getBoolean("enable_fire"));
		tnt.setFuseTicks(0);
		bombSites.put(tnt, player.getName());
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			@Override
			public void run() {
				bombSites.remove(tnt);
			}}, 10L);
		//player.getWorld().createExplosion(loc, (float) config.getDouble("power"), config.getBoolean("enable_fire"));
	    return true; 
	}
	
	@Override
	public boolean enable(Server serv) {
		bombSites = new HashMap<TNTPrimed, String>();
		Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
		return true;
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerDeath(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && 
				event.getDamager() instanceof TNTPrimed && 
				((Player)event.getEntity()).getHealth() - event.getDamage() <= 0 &&
				bombSites.containsKey((TNTPrimed) event.getDamager()) &&
				Bukkit.getPlayer(bombSites.get((TNTPrimed) event.getDamager())) != null) {
			((Player) event.getEntity()).damage(event.getDamage(), Bukkit.getPlayer(bombSites.get((TNTPrimed) event.getDamager())));
			event.setCancelled(true);
		}
	}
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    	if (event.getEntity() instanceof Player && 
    			event.getDamager() instanceof TNTPrimed &&
    			bombSites.containsKey((TNTPrimed) event.getDamager()) &&
    			bombSites.get((TNTPrimed) event.getDamager()).equalsIgnoreCase(((Player) event.getEntity()).getName()))
    		event.setCancelled(true);
    		
    }
	
}
