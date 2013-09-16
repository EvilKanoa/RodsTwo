package ca.kanoa.rodstwo.Rods;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;

public class Companion extends Rod implements Listener {

	public Companion() throws Exception {
		super("Companion", 1, 280, new ConfigOptions(new String[]{"number_to_spawn"}, new Object[]{2}), 500);
		super.setRecipe(new ShapedRecipe(super.getItem()).shape(" W ", "WBW", " W ").setIngredient('W', Material.BONE).setIngredient('B', Material.STICK));
	}

	@Override
	public boolean run(Player player, ConfigurationSection config) {
		for (int i = 0; i < config.getInt("number_to_spawn"); i++) {
			Wolf wolf = player.getWorld().spawn(player.getLocation(), Wolf.class);
			wolf.setTamed(true);
			wolf.setOwner(player);

			if (RodsTwo.useMobDeathAsPlayer) {
				wolf.setCustomName(player.getName());
				wolf.setCustomNameVisible(true);
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
		if (event.getEntity() instanceof Player && event.getDamager() instanceof org.bukkit.entity.Wolf) {
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
