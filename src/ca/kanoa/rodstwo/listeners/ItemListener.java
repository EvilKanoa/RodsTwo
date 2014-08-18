package ca.kanoa.rodstwo.listeners;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ca.kanoa.rodstwo.helpers.Utils;
import ca.kanoa.rodstwo.rods.Rod;

public class ItemListener implements Listener {

	@EventHandler
	public void onPlayerDropItemStack(PlayerDropItemEvent event) {
		if (Utils.getRod(event.getItemDrop().getItemStack()) != null &&
				event.getItemDrop().getItemStack().getAmount() > 1) {
			ItemStack top = new ItemStack(event.getItemDrop().getItemStack());
			ItemStack bottom = new ItemStack(event.getItemDrop().getItemStack());
			top.setAmount(1);
			bottom.setAmount(bottom.getAmount() - 1);
			Rod.setUses(bottom, Utils.getRod(bottom).getUses());

			Vector vect = event.getItemDrop().getVelocity();
			int ticks = event.getItemDrop().getTicksLived();
			event.getItemDrop().remove();
			Item topItem = event.getItemDrop().getWorld().dropItem(event.getItemDrop().getLocation(), top);
			Item botItem = event.getItemDrop().getWorld().dropItem(event.getItemDrop().getLocation(), bottom);
			topItem.setVelocity(vect);
			botItem.setVelocity(vect);
			topItem.setTicksLived(ticks);
			botItem.setTicksLived(ticks);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (Utils.getRod(event.getItemDrop().getItemStack()) != null &&
				event.getItemDrop().getItemStack().getAmount() == 1 &&
				Utils.getRod(event.getPlayer().getItemInHand()) != null) {
			ItemStack rod = event.getPlayer().getItemInHand();
			Rod.setUses(rod, Utils.getRod(event.getPlayer().getItemInHand()).getUses());
			event.getPlayer().setItemInHand(rod);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (Utils.getRod(event.getItem().getItemStack()) != null) {
			Rod rod = Utils.getRod(event.getItem().getItemStack());
			ItemStack[] contents = event.getPlayer().getInventory().getContents();
			for (int i = 0; i < contents.length; i++) {
				if (Utils.getRod(contents[i]) == rod) {
					contents[i].setAmount(contents[i].getAmount() + 1);
					Rod.setUses(contents[i], Rod.getUses(event.getItem().getItemStack()));
					event.setCancelled(true);
					event.getItem().remove();
				}
			}
		}
	}
	
}
