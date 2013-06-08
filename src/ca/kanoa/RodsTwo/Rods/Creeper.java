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

public class Creeper extends Rod implements Listener {

	public Creeper(Plugin plugin) throws Exception {
	    super("Creeper", 1, 280, new ConfigOptions(new String[]{"number_to_spawn", "max_distence"}, new Object[]{1, 50}), 500, plugin);
	    setRecipe(new ShapedRecipe(super.getItem()).shape(" G ", "GBG", " G ").setIngredient('G', Material.SULPHUR).setIngredient('B', Material.STICK));
	}
	
	@Override
	public boolean run(Player player, ConfigurationSection config) {
	   	for (int i = 0; i < config.getInt("number_to_spawn"); i++) {
	   		Location loc = player.getTargetBlock(null, config.getInt("max_distence")).getLocation();
	   		loc.add(0, 1.2f, 0);
	   		org.bukkit.entity.Creeper creeper = player.getWorld().spawn(loc, org.bukkit.entity.Creeper.class);
	   		
	   		if (RodsTwo.useMobDeathAsPlayer) {
	   			creeper.setCustomName(player.getName());
	   			creeper.setCustomNameVisible(true);
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
		if (event.getEntity() instanceof Player && event.getDamager() instanceof org.bukkit.entity.Creeper) {
			LivingEntity entity = (LivingEntity) event.getDamager();
			Player player = (Player) event.getEntity();
			for (Player p : Bukkit.getOnlinePlayers())
				if (entity.getCustomName().equalsIgnoreCase(p.getName()) && 
						!entity.getCustomName().equalsIgnoreCase(player.getName())) {
					if (player.getHealth() - event.getDamage() <= 0) {
						player.damage(event.getDamage(), p);
						event.setCancelled(true);
						return;
					}
				}
				else if (entity.getCustomName().equalsIgnoreCase(player.getName()))
					event.setCancelled(true);
		}
	}
}
