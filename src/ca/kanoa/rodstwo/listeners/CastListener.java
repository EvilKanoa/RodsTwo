package ca.kanoa.rodstwo.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.events.PlayerUseRodEvent;
import ca.kanoa.rodstwo.helpers.RodCooldown;
import ca.kanoa.rodstwo.helpers.Utils;
import ca.kanoa.rodstwo.rods.Rod;

public class CastListener implements Listener {

	private RodsTwo plugin;

	public CastListener(){
		this.plugin = RodsTwo.getInstance();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			try {
				if (RodsTwo.noFire.contains(event.getClickedBlock().getType())) {
					return;
				}
			} catch (NullPointerException e) {}

			Rod rod = Utils.getRod(event.getItem());
			if (rod != null &&
					(event.getPlayer().hasPermission(rod.getUsePermission()) || 
					event.getPlayer().hasPermission("lr.use.all")) &&
					plugin.rodConfig.getBoolean(rod.getPath("enabled"))) {
				if (rod.run(event.getPlayer(), plugin.rodConfig.getConfigurationSection("Rods." + rod.getName() + ".options"))) {
					if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
						ItemStack is = event.getItem();
						Rod.changeUses(is, Rod.getUses(is) != -1 ? -1 : 0);
						if (Rod.getUses(event.getItem()) == 0) {
							is.setAmount(is.getAmount() - 1);
							Rod.setUses(is, rod.getUses());
						}
						event.getPlayer().setItemInHand(is);
					}

					if (!event.getPlayer().hasPermission("lr.cooldown.exempt") && 
							event.getPlayer().getItemInHand() != null &&
							event.getPlayer().getItemInHand().getAmount() >= 1) {
						RodCooldown cooldown = new RodCooldown(event.getPlayer(), rod.getCooldown());
						int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(RodsTwo.getInstance(), cooldown, 20, 20);
						cooldown.setTaskID(taskID);
					}

					Bukkit.getPluginManager().callEvent(new PlayerUseRodEvent(event.getPlayer(), rod));

				}
			}
		}
	}

}
