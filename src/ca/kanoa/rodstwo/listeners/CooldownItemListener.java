package ca.kanoa.rodstwo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import ca.kanoa.rodstwo.helpers.RodCooldown;

public class CooldownItemListener implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (RodCooldown.isCooldownItem(event.getItemDrop().getItemStack())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (RodCooldown.isCooldownItem(event.getCurrentItem())) {
			event.setCancelled(true);
		}
	}
	
}
