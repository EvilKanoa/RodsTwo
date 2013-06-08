package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.RodsTwo;
import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Zombie extends Rod implements Listener {

	public Zombie(Plugin plugin) throws Exception {
	    super("Zombie", 1, 280, new ConfigOptions(new String[]{"number_to_spawn", "max_distence"}, new Object[]{2, 50}), 500, plugin);
	    setRecipe(new ShapedRecipe(super.getItem()).shape("RRR", "RBR", "RRR").setIngredient('R', Material.ROTTEN_FLESH).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	   	for (int i = 0; i < config.getInt("number_to_spawn"); i++) {
	   		Location loc = player.getTargetBlock(null, config.getInt("max_distence")).getLocation();
	   		loc.add(0, 1.2f, 0);
	   		org.bukkit.entity.Zombie zombie = player.getWorld().spawn(loc, org.bukkit.entity.Zombie.class);
	   		
	   		if (RodsTwo.useMobDeathAsPlayer) {
	   			zombie.setCustomName(player.getName());
	   			zombie.setCustomNameVisible(true);
			}
	   	}
	   	return true;
	}
	
	@Override
	public boolean enable(Server serv) {
		if (RodsTwo.useMobDeathAsPlayer)
			Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
		return true;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerDeath(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof org.bukkit.entity.Zombie) {
			LivingEntity entity = (LivingEntity) event.getDamager();
			Player player = (Player) event.getEntity();
			for (Player p : Bukkit.getOnlinePlayers())
				if (entity.getCustomName().equalsIgnoreCase(p.getName())) 
					if (((Player)event.getEntity()).getHealth() - event.getDamage() <= 0) {
						player.damage(event.getDamage(), p);
						event.setCancelled(true);
						return;
					}
			
		}
	}
}
